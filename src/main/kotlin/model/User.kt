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
    "_id": {
        "$oid": "6822843f5e65dd4913a66c07"
    },
    "userId": "h85boZM3lQhDs94rpS6MJvcvZSi2",
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
                "$date": "2025-05-12T23:29:03.112Z"
            }
        }
    ],
    "lastLogin": {
        "$date": "2025-05-12T23:29:03.112Z"
    },
    "loginProvider": "google",
    "createdAt": {
        "$date": "2025-05-12T23:29:03.112Z"
    },
    "updatedAt": {
        "$date": "2025-05-12T23:29:03.112Z"
    },
    "physicalAttributes": {
        "age": 28,
        "weight": 70.5,
        "height": 175,
        "bloodType": "O+",
        "gender": "male"
    },
    "contactInfo": {
        "phone": "+63 912 345 6789",
        "address": {
            "street": "123 Main Street",
            "city": "Ormoc",
            "province": "Eastern Visayas",
            "country": "Philippines",
            "zipCode": "6541"
        }
    },
    "preferences": {
        "language": "English",
        "theme": "dark"
    },
    "socialLinks": {
        "facebook": "https://facebook.com/keaneradleigh",
        "twitter": "https://twitter.com/keaneradleigh"
    },
    "employment": {
        "jobTitle": "Software Engineer",
        "company": "Tech Innovations Inc.",
        "workEmail": "keane@techinnovations.com"
    },
    "education": {
        "degree": "Bachelor of Science in Computer Science",
        "institution": "University of the Philippines",
        "graduationYear": 2022
    },
    "biography": "I'm a very friendly person. K bye"
}
*/
@Serializable
@JsonIgnoreUnknownKeys
data class User(
    val userId: String,
    val email: String,
    val name: String,
    val profilePicture: String? = null,
    val roles: List<String> = listOf("user"),
    val accountStatus: String = "active",
    val activityLog: List<ActivityLog> = listOf(),
    val lastLogin: TimeStamp? = null,
    val loginProvider: String,
    val createdAt: TimeStamp,
    val updatedAt: TimeStamp? = null,
    val physicalAttributes: PhysicalAttributes? = null,
    val contactInfo: ContactInfo? = null,
    val preferences: Preferences? = null,
    val socialLinks: SocialLinks? = null,
    val employment: Employment? = null,
    val education: Education? = null,
    val biography: String? = null
) {
    fun toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): User = Json.decodeFromString(document.toJson())
    }
}

@Serializable
data class PhysicalAttributes(
    val age: Int? = null,
    val weight: Double? = null,
    val height: Int? = null,
    val bloodType: String? = null,
    val gender: String? = null
)

@Serializable
data class ContactInfo(
    val phone: String? = null,
    val address: Address? = null
)

@Serializable
data class Address(
    val street: String? = null,
    val city: String? = null,
    val province: String? = null,
    val country: String? = null,
    val zipCode: String? = null
)

@Serializable
data class Preferences(
    val language: String? = null,
    val theme: String? = null
)

@Serializable
data class SocialLinks(
    val facebook: String? = null,
    val twitter: String? = null
)

@Serializable
data class Employment(
    val jobTitle: String? = null,
    val company: String? = null,
    val workEmail: String? = null
)

@Serializable
data class Education(
    val degree: String? = null,
    val institution: String? = null,
    val graduationYear: Int? = null
)

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
