package main

import com.codeborne.selenide.Configuration
import org.leadium.extensions.Selenoid
import utils.FileFinder
import java.io.BufferedReader
import java.io.FileReader
import java.util.*

object Configuration {

    private val p: Properties by lazy {
        val properties = Properties()
        BufferedReader(FileReader(FileFinder() byEndOfPath "configuration.property"))
            .use { properties.load(it) }
        properties
    }

    val interval = p.getProperty("interval")!!.toLong()

}