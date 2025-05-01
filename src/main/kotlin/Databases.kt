package com.capstone

import com.capstone.auth.verifyToken
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

fun Application.configureDatabases() {
    val mongoDatabase = connectToMongoDB() // TODO - refactor this
    val carService = CarService(mongoDatabase)
    routing {
        get("/users/{name}") {
            val name = call.parameters["name"] ?: throw IllegalArgumentException("No name found")
            val collection = mongoDatabase.getCollection("users")
            val user = collection.find(Document("name", name)).first()
            if (user != null) {
                call.respond(user.toJson())
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
    val user = environment.config.tryGetString("db.mongo.user")
    val password = environment.config.tryGetString("db.mongo.password")
    val host = environment.config.tryGetString("db.mongo.host") ?: "127.0.0.1"
    val port = environment.config.tryGetString("db.mongo.port") ?: "27017"
    val maxPoolSize = environment.config.tryGetString("db.mongo.maxPoolSize")?.toInt() ?: 20
    val databaseName = environment.config.tryGetString("db.mongo.database.name") ?: "users"

    // TODO: review and refactor code
    val credentials = user?.let { userVal -> password?.let { passwordVal -> "$userVal:$passwordVal@" } }.orEmpty()
//    val uri = "mongodb://$credentials$host:$port/?maxPoolSize=$maxPoolSize&w=majority"
     val uri = "mongodb+srv://admin:123@envirocluster.ziwxmi9.mongodb.net/?retryWrites=true&w=majority&appName=EnviroCluster"

    val mongoClient = MongoClients.create(uri)
    val database = mongoClient.getDatabase("enviro")

    monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }

    return database
}
