package adventOfCode

import java.io.File

class Utils {
    companion object {
        fun readFileAsTextUsingInputStream(fileName: String)
                = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8);
        fun readFileAsLinesUsingUseLines(fileName: String): List<String>
                = File(fileName).useLines { it.toList() }
    }

}