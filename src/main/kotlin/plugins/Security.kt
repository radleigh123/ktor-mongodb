package com.capstone.plugins

import com.capstone.auth.MyAuthenticatedUser
import com.kborowy.authprovider.firebase.firebase
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import java.io.File

fun Application.configureSecurity() {
    install(Authentication) {
        firebase {
            adminFile = File(System.getenv("AUTH_FIREBASE_SERVICE_KEY"))
            realm = "My Server"
            validate { token ->
                token?.let { MyAuthenticatedUser(it.uid) }
            }
        }
    }
}
