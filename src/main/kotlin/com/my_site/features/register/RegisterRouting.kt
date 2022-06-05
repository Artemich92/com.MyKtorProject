package com.my_site.features.register

import com.my_site.cache.InMemoryCache
import com.my_site.cache.TokenCache
import com.my_site.utils.isValidEmail
import com.my_site.utils.isValidPassword
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.util.UUID

fun Application.configureRegisterRouting() {

    routing {
        post("/register") {
            val receive = call.receive<RegisterReceiveRemote>()
            if (!receive.email.isValidEmail()) {
                call.respond(HttpStatusCode.BadRequest, "Email is not valid")
                return@post
            }

            if (InMemoryCache.users.map { it.login }.contains(receive.login)) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
                return@post
            }

            if (!receive.password.isValidPassword()) {
                call.respond(HttpStatusCode.Conflict, "Password is not valid")
                return@post
            }

            val token = UUID.randomUUID().toString()
            InMemoryCache.users.add(receive)
            InMemoryCache.tokens.add(TokenCache(receive.login, token))
            call.respond(HttpStatusCode.OK, RegisterResponseRemote(token))
        }
    }
}
