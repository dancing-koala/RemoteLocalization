import java.io.BufferedWriter
import java.io.File

class AndroidLocalizableFileWriter(private val outDirFile: File, val defaultLanguage: Language) : LocalizableFileWriter {

    private val stringBuilder = StringBuilder()

    override fun getLanguageFile(language: String): File =
        if (language == defaultLanguage.code) {
            File(outDirFile, "values/strings.xml")
        } else {
            File(outDirFile, "values-$language/strings.xml")
        }

    override fun writeHeader(writer: BufferedWriter) {
        writer.apply {
            write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
            write("<resources>\n")
        }
    }

    override fun writeTranslation(writer: BufferedWriter, translation: Translation) {
        stringBuilder.clear()

        val processedValue = processValue(translation.value)

        if (translation.comment.isNotBlank()) {
            stringBuilder.append("    <!-- ${translation.comment} -->\n")
        }

        stringBuilder.append("    <string name=\"${translation.name}\">\"${processedValue}\"</string>\n")

        writer.write(stringBuilder.toString())
    }

    override fun writeFooter(writer: BufferedWriter) {
        writer.write("</resources>")
    }


    fun processValue(value: String) = value
        .replace("%@", "%s")
        .replace("&", "&amp;")

}