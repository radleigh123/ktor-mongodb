package com.capstone.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import org.bson.Document

/*
DOCUMENT EXAMPLE
{
    "_id": "h85boZM3lQhDs94rpS6MJvcvZSi2",
    "email": "keaneradleigh@gmail.com",
    "name": "Keane Radleigh",
    "profilePicture": "https://example.com/profile.jpg",
    "roles": [
    "user"
    ],
    "accountStatus": "active",
    "activityLog": [
    {
        "action": "signed up",
        "timestamp": {
        "$date": "2025-05-01T20:50:05.657Z"
    }
    }
    ],
    "lastLogin": {
    "$date": "2025-05-01T20:50:05.657Z"
},
    "loginProvider": "google",
    "createdAt": {
    "$date": "2025-05-01T20:50:05.657Z"
},
    "updatedAt": {
    "$date": "2025-05-01T20:50:05.657Z"
}
}
*/

@Serializable
@JsonIgnoreUnknownKeys
data class User(
    val userId: String,
    val email: String,
    val name: String,
    val profilePicture: String?,
    val roles: List<String> = listOf(),
    val accountStatus: String,
    val activityLog: List<ActivityLog> = listOf(),
    val lastLogin: TimeStamp,
    val loginProvider: String,
    val createdAt: TimeStamp,
    val updatedAt: TimeStamp
) {
    fun toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): User = Json.decodeFromString(document.toJson())
    }
}

@Serializable
data class ActivityLog(
    val action: String,
    val timestamp: TimeStamp
)

@Serializable
data class TimeStamp(
    @SerialName("\$date")
    val date: String
)
