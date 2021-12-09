import java.io.BufferedWriter
import java.io.File

interface LocalizableFileWriter {
    fun getLanguageFile(language: String): File
    fun writeHeader(writer: BufferedWriter)
    fun writeTranslation(writer: BufferedWriter, translation: Translation)
    fun writeFooter(writer: BufferedWriter)
}