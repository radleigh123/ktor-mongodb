package com.capstone.di

// MARK: FUTURE USE
// This is a Koin module for dependency injection in a Ktor application.
/*
import com.capstone.controller.UserController
import com.capstone.model.User
import com.capstone.repository.UserRepository
import com.capstone.services.UserService
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.ktor.server.config.tryGetString
import io.ktor.server.engine.applicationEnvironment
import org.koin.dsl.module

val mongoModule = module {
    single<MongoClient> {
        val user = System.getenv("DB_MONGO_USER") ?: "default"
        val password = System.getenv("DB_MONGO_PASSWORD") ?: "default123"
        val host = "127.0.0.1"

        val credentials = user?.let { userVal -> password?.let { passwordVal -> "$userVal:$passwordVal" } }.orEmpty()

        // TODO: REMOVE THIS AFTER TESTING
        println("""
        MongoDB connection parameters:
        user: $user
        password: $password
        host: $host
        credentials: $credentials
    """.trimIndent())

//    val uri = "mongodb://$credentials$host:$port/?maxPoolSize=$maxPoolSize&w=majority" // uncomment for local MongoDB
//        val uri = "mongodb+srv://$credentials@$host/?retryWrites=true&w=majority&appName=EnviroCluster" // uncomment for MongoDB Atlas

        MongoClients.create(System.getenv("DB_MONGO_URI"))
    }

    single<MongoDatabase> {
        get<MongoClient>().getDatabase("enviro")
    }
    single<MongoClient> { MongoClients.create(System.getenv("DB_MONGO_URI")) }
    single { get<MongoClient>().getDatabase("enviro") }
    single { get<MongoDatabase>().getCollection("users", User::class.java) }
}

val appModule = module {
    single { UserRepository(get()) }
    single { UserService(get()) }
    single { UserController(get()) }
}

val userModule = module {
    */
/*single<MongoDatabase> {
        connectToMongoDB()
    }*//*

    single { get<MongoDatabase>().getCollection("users", User::class.java) }
    single { UserRepository(get()) }
    single { UserService(get()) }
    single { UserController(get()) }
}
*/
