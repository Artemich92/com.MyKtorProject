package com.my_site

import com.my_site.features.login.configureLoginRouting
import com.my_site.features.register.configureRegisterRouting
import com.my_site.plugins.configureRouting
import com.my_site.plugins.configureSerialization
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureLoginRouting()
        configureRegisterRouting()
        configureSerialization()
    }.start(wait = true)
}
