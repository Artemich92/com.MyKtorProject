package com.my_site.features.login

import com.my_site.cache.InMemoryCache
import com.my_site.cache.TokenCache
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.util.UUID

fun Application.configureLoginRouting() {

    routing {
        post("/login") {
            val receive = call.receive<LoginReceiveRemote>()
            val first = InMemoryCache.users.firstOrNull { it.login == receive.login }

            if (first == null) {
                call.respond(HttpStatusCode.BadRequest, "User Not Found")
            } else {
                if (first.password == receive.password) {
                    val token = UUID.randomUUID().toString()
                    InMemoryCache.tokens.add(TokenCache(receive.login, token))
                    call.respond(HttpStatusCode.OK, LoginResponseRemote(token))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid password")
                }
            }
        }
    }
}

