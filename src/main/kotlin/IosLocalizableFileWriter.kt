import java.io.BufferedWriter
import java.io.File

class IosLocalizableFileWriter(private val outDirFile: File) : LocalizableFileWriter {

    private val stringBuilder = StringBuilder()

    override fun getLanguageFile(language: String): File =
        File(outDirFile, "$language.lproj/Localizable.strings")

    override fun writeHeader(writer: BufferedWriter) = Unit

    override fun writeTranslation(writer: BufferedWriter, translation: Translation) {
        stringBuilder.clear()

        if (translation.comment.isNotBlank()) {
            stringBuilder.append("// ${translation.comment}\n")
        }

        stringBuilder.append("\"${translation.name}\"=\"${translation.value}\";\n")

        writer.write(stringBuilder.toString())
    }

    override fun writeFooter(writer: BufferedWriter) = Unit
}