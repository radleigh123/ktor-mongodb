package com.capstone

import com.capstone.auth.initFirebase
import com.capstone.plugins.configureSecurity
import com.capstone.plugins.configureDatabases
import com.capstone.plugins.configureMonitoring
import com.capstone.plugins.configureRouting
import com.capstone.plugins.configureSerialization
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    // TODO: Duplicate initialization of Firebase
    initFirebase()
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureRouting()
    configureDatabases()
}
