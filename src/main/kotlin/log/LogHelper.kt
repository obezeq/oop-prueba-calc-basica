package es.iesraprog2425.pruebaes.log

import java.io.File

class LogHelper(private val logFile: File) {
    fun log(message: String) {
        logFile.appendText(message + "\n")
    }
}
