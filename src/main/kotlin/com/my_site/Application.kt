package com.my_site

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import com.my_site.plugins.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}
