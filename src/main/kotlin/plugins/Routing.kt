package com.capstone.plugins

import com.capstone.auth.MyAuthenticatedUser
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        authenticate {
            get("/protected") {
                val user = call.principal<MyAuthenticatedUser>()
                if (user != null) {
                    call.respond(HttpStatusCode.OK, "Hello, ${user.id}!")
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "User not authenticated")
                }
            }
        }
    }
}
