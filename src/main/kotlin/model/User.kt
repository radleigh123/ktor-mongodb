package com.capstone.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bson.Document

@Serializable
data class User(
    val id: String,
    val email: String,
    val name: String,
    val profilePicture: String?,
    val roles: List<String> = listOf("user"),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    fun toDocument(): Document {
        return Document.parse(Json.encodeToString(this))
    }

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): User {
            return Json.decodeFromString(document.toJson())
        }
    }
}
