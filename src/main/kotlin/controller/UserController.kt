package com.capstone.controller

import com.capstone.model.User
import com.capstone.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.log
import io.ktor.server.request.receive
import io.ktor.server.response.respond

class UserController(
    private val userService: UserService
) {
    suspend fun getAllUsers(call: ApplicationCall) {
        try {
            userService.getAllUsers()?.let { users ->
                call.respond(users)
            } ?: call.respond(HttpStatusCode.NotFound, mapOf("message" to "No users found"))
        } catch (e: Exception) {
            call.application.log.error("Error fetching users: ${e.message}")
            call.respond(HttpStatusCode.InternalServerError, "Error fetching users")
        }
    }

    suspend fun createUser(call: ApplicationCall) {
        try {
            val user = call.receive<User>()
            val id = userService.createUser(user)
            call.respond(HttpStatusCode.Created, mapOf("message" to "User created", "id" to id))
        } catch (e: Exception) {
            call.application.log.error("Error creating user: ${e.message}")
            call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Failed to create user"))
        }
    }

    suspend fun getUserById(call: ApplicationCall) {
        try {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
            userService.getUserById(id)?.let { user ->
                call.respond(user)
            } ?: call.respond(HttpStatusCode.NotFound, mapOf("message" to "User not found"))
        } catch (e: Exception) {
            call.application.log.error("Error retrieving user with ID: ${call.parameters["id"]}", e)
            call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Failed to retrieve user"))
        }
    }

}