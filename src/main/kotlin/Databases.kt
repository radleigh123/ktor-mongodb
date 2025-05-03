package com.capstone

import com.capstone.auth.verifyToken
import com.capstone.di.userModule
import com.capstone.model.User
import com.capstone.repository.UserRepository
import com.capstone.services.UserService
import com.kborowy.authprovider.firebase.firebase
import com.mongodb.client.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.bson.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.io.File
import org.slf4j.event.*
import org.koin.core.context.startKoin
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin

fun Application.configureDatabases() {
    // Start Koin and load modules
    // Empty module for now
    install(Koin) {
        modules(userModule)
    }

    val mongoDatabase = connectToMongoDB()
    val carService = CarService(mongoDatabase) // Initialize CarService with MongoDB
    val collection: MongoCollection<User> = mongoDatabase.getCollection("users", User::class.java)
    val userRepository = UserRepository(collection)
    val userService = UserService(userRepository)
    
//    val userService = get<UserService>() // Retrieve UserService from Koin

    routing {
        get("/users/{name}") {
            val name = call.parameters["name"] ?: throw IllegalArgumentException("No name found")
            val user = userService.getUserById(name) // Use UserService
            if (user != null) {
                call.respond(user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        post("/users/verify") {
            val idToken = call.receive<Map<String, String>>()["idToken"]
                ?: return@post call.respond(mapOf("error" to "Invalid token"))

            val uid = verifyToken(idToken)

            if (uid != null) {
                call.respond(mapOf("success" to true, "uid" to uid))
//                call.respond(uid.toString())
            } else {
                call.respond(mapOf("error" to "Invalid token"))
            }
        }

        get("/users") {
            val collection = mongoDatabase.getCollection("users")
            val user = collection.find().first()

            if (user != null) {
                var pass = "test123"
                call.respond(user.toJson())
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        // Create car
        post("/cars") {
            val car = call.receive<Car>()
            val id = carService.create(car)
            call.respond(HttpStatusCode.Created, id)
        }
        // Read car
        get("/cars/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
            carService.read(id)?.let { car ->
                call.respond(car)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
        // Update car
        put("/cars/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
            val car = call.receive<Car>()
            carService.update(id, car)?.let {
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
        // Delete car
        delete("/cars/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
            carService.delete(id)?.let {
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
    }
}

/**
 * Establishes connection with a MongoDB database.
 *
 * The following configuration properties (in application.yaml/application.conf) can be specified:
 * * `db.mongo.user` username for your database
 * * `db.mongo.password` password for the user
 * * `db.mongo.host` host that will be used for the database connection
 * * `db.mongo.port` port that will be used for the database connection
 * * `db.mongo.maxPoolSize` maximum number of connections to a MongoDB server
 * * `db.mongo.database.name` name of the database
 *
 * IMPORTANT NOTE: in order to make MongoDB connection working, you have to start a MongoDB server first.
 * See the instructions here: https://www.mongodb.com/docs/manual/administration/install-community/
 * all the paramaters above
 *
 * @returns [MongoDatabase] instance
 * */
fun Application.connectToMongoDB(): MongoDatabase {
    val user = System.getenv("DB_MONGO_USER") ?: "default"
    val password = System.getenv("DB_MONGO_PASSWORD") ?: "default123"
    val host = environment.config.tryGetString("db.mongo.host") ?: "127.0.0.1"
    val port = environment.config.tryGetString("db.mongo.port") ?: "27017"
    val maxPoolSize = environment.config.tryGetString("db.mongo.maxPoolSize")?.toInt() ?: 20
    val databaseName = environment.config.tryGetString("db.mongo.database.name") ?: "myDatabase"

    val credentials = user?.let { userVal -> password?.let { passwordVal -> "$userVal:$passwordVal" } }.orEmpty()

    // TODO: REMOVE THIS AFTER TESTING
    println("""
        MongoDB connection parameters:
        user: $user
        password: $password
        credentials: $credentials
        host: $host
        port: $port
        maxPoolSize: $maxPoolSize
        databaseName: $databaseName
    """.trimIndent())

//    val uri = "mongodb://$credentials$host:$port/?maxPoolSize=$maxPoolSize&w=majority" // uncomment for local MongoDB
    val uri = "mongodb+srv://$credentials@$host/?retryWrites=true&w=majority&appName=EnviroCluster" // uncomment for MongoDB Atlas

    val mongoClient = MongoClients.create(uri)
    val database = mongoClient.getDatabase(databaseName)

    monitor.subscribe(ApplicationStopped) {
        println("Closing MongoDB connection...")
        mongoClient.close()
    }

    return database
}

