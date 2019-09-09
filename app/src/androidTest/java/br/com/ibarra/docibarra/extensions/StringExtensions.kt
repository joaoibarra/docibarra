package br.com.ibarra.docibarra.extensions

import androidx.test.platform.app.InstrumentationRegistry
import java.io.IOException

fun String.loadResponse(): String {
    try {
        val stream = InstrumentationRegistry.getInstrumentation()
                .context
                .resources
                .assets
                .open("mockresponses/$this")

        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        return String(buffer)
    } catch (e: IOException) {
        throw e
    }
}