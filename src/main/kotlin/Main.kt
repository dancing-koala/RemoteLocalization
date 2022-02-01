import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.FileOutputStream
import java.nio.file.Paths

typealias TranslationData = Map<String, List<Translation>>

private const val COLUMN_NAME = "Asset ID"
private const val COLUMN_COMMENT = "Comment"

fun main(args: Array<String>) {
    GenerateLocalizationFiles().main(args)
}

class GenerateLocalizationFiles : CliktCommand() {

    private val currentWorkingDirectory = Paths.get("").toFile()

    private val android: Boolean by option(help = "Generate localization files for Android").flag()
    private val ios: Boolean by option(help = "Generate localization files for iOs").flag()
    private val url: String by option(help = "URL of the csv file to parse").required()

    private val androidDir by option(help = "Target directory for Android files")
        .file(mustExist = true, canBeDir = true, canBeFile = false).default(currentWorkingDirectory)

    private val iosDir by option(help = "Target directory for iOs files")
        .file(mustExist = true, canBeDir = true, canBeFile = false).default(currentWorkingDirectory)

    override fun run() {
        if (!android && !ios) {
            println("Nothind to do, exiting! Good bye :)")
            return
        }

        val start = System.currentTimeMillis()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.NONE })
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        val response = okHttpClient.newCall(request).execute()

        val bodyInputStream = response.body?.byteStream()
            ?: throw IllegalArgumentException("body is null")

        val columns = csvReader().readAllWithHeader(bodyInputStream)

        val translationsByLanguages = mutableMapOf<String, List<Translation>>()

        Language.values().forEach { language ->
            translationsByLanguages[language.code] = columns.mapNotNull {
                val name = it[COLUMN_NAME]!!

                if(name.isBlank()) {
                    return@mapNotNull null
                }

                Translation(
                    name = name,
                    comment = it[COLUMN_COMMENT]!!,
                    value = it[language.columnName]?.trim() ?: ""
                )
            }
        }

        if (android) {
            writeFiles(
                localizableFileWriter = AndroidLocalizableFileWriter(outDirFile = androidDir, defaultLanguage = Language.EN),
                data = translationsByLanguages
            )

            println("Files generated for android in [$androidDir] !")
        }

        if (ios) {
            writeFiles(
                localizableFileWriter = IosLocalizableFileWriter(outDirFile = iosDir),
                data = translationsByLanguages
            )

            println("Files generated for ios in [$iosDir] !")
        }

        println("Done! Took ${System.currentTimeMillis() - start}ms")
    }

    private fun writeFiles(localizableFileWriter: LocalizableFileWriter, data: TranslationData) {
        data.keys.forEach { language ->

            val languageFile = localizableFileWriter.getLanguageFile(language).apply {
                mkdirs()

                if (exists()) {
                    delete()
                }

                createNewFile()
            }

            FileOutputStream(languageFile).use {
                val writer = it.bufferedWriter()

                localizableFileWriter.writeHeader(writer)
                writer.flush()

                data[language]?.forEach { translation ->
                    localizableFileWriter.writeTranslation(writer, translation)
                    writer.flush()
                }

                localizableFileWriter.writeFooter(writer)
                writer.flush()
            }
        }
    }
}
