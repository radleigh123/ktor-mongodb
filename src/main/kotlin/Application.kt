package com.capstone

import com.capstone.auth.initFirebase
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
//    configureSecurity()
    initFirebase()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureRouting()
}
