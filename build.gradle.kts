val kotlin_version: String by project
val logback_version: String by project
val mongo_version: String by project

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.1.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
}

group = "com.capstone"
version = "0.0.1"

// MARK: Specifies the version of the Ktor framework to use
val ktor_version = "3.1.2"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")

    implementation("org.mongodb:mongodb-driver-core:$mongo_version")
    implementation("org.mongodb:mongodb-driver-sync:$mongo_version")
    implementation("org.mongodb:bson:$mongo_version")
    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson:$ktor_version")
    implementation("com.google.firebase:firebase-admin:9.4.3")
    implementation("com.kborowy:firebase-auth-provider:1.5.0")

    implementation("io.insert-koin:koin-ktor3:4.1.0-Beta7") // Latest beta version
    implementation("ch.qos.logback:logback-classic:${logback_version}")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.6")

    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
