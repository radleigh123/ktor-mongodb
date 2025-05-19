<p><a target="_blank" href="https://app.eraser.io/workspace/KvOgWEvW6hYVdWV2GSHw" id="edit-in-eraser-github-link"><img alt="Edit in Eraser" src="https://firebasestorage.googleapis.com/v0/b/second-petal-295822.appspot.com/o/images%2Fgithub%2FOpen%20in%20Eraser.svg?alt=media&amp;token=968381c8-a7e7-472a-8ed6-4a6626da5501"></a></p>

# User API Documentation
This document outlines the structure, endpoints, and usage of the User API built with Ktor and MongoDB. The API supports operations to manage user profiles, retrieve information by various filters, and update user data.

## Overview
The User API exposes RESTful endpoints to manage user data stored in a MongoDB collection. It supports creating users, retrieving all or specific users (by ID or email), and updating user records.

The user model includes both standard identity fields and extended profile information such as contact details, social media links, employment, education, and physical attributes.

## Base URL
```
http://<your-domain>/  # Replace with actual host and port
```
## Data Model
The primary data model is `User`, a Kotlin data class annotated with `@Serializable`. This allows seamless JSON serialization and deserialization using Kotlinx.

### Example User Document (MongoDB)
```json
{
  "userId": "h85boZM3lQhDs94rpS6MJvcvZSi2",
  "email": "keaneradleigh@gmail.com",
  "name": "Keane Radleigh",
  "profilePicture": "https://example.com/profile.jpg",
  "roles": ["user"],
  "accountStatus": "active",
  "activityLog": [
    {
      "action": "signed up",
      "timestamp": { "$date": "2025-05-12T23:29:03.112Z" }
    }
  ],
  "lastLogin": { "$date": "2025-05-12T23:29:03.112Z" },
  "loginProvider": "google",
  "createdAt": { "$date": "2025-05-12T23:29:03.112Z" },
  "updatedAt": { "$date": "2025-05-12T23:29:03.112Z" },
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
```
## Endpoints
### 1. `GET /users` 
**Description**: Retrieve a list of all users.

**Response**: 

- `200 OK` : Returns a JSON array of user objects.
- `500 Internal Server Error` : Server failure or DB connection issue.
### 2. `GET /user/{id}` 
**Description**: Retrieve a specific user by their database `_id`.

**Path Parameter**:

- `id` : The MongoDB ObjectId of the user document.
**Response**:

- `200 OK` : User object.
- `404 Not Found` : If user does not exist.
### 3. `GET /user/email/{email}` 
**Description**: Retrieve a user by email address.

**Path Parameter**:

- `email` : Email of the user.
**Response**:

- `200 OK` : User object.
- `404 Not Found` : No user with the given email.
### 4. `POST /user` 
**Description**: Create a new user entry in the database.

**Request Body**:
A JSON payload representing the `User` model (partial fields allowed due to nullable support).

**Response**:

- `201 Created` : User created successfully.
- `400 Bad Request` : Invalid payload.
- `500 Internal Server Error` : DB error or exception.
### 5. `PUT /user/uid/{userId}` 
**Description**: Update user fields using their `userId` (not MongoDB `_id`).

**Path Parameter**:

- `userId` : The Firebase or custom user identifier.
**Request Body**:

- JSON object with fields to update. Only provided fields are modified.
**Response**:

- `200 OK` : User updated.
- `404 Not Found` : No user with given `userId` .
- `400 Bad Request` : Malformed request or missing fields.
## Data Conversion Utilities
In the `User` model:

- `toDocument()` : Serializes the Kotlin `User`  object into a MongoDB `Document` .
- `fromDocument(document)` : Deserializes a MongoDB document into a Kotlin `User` .
This helps in working directly with MongoDB while maintaining clean code.

## MongoDB Collection
Your users are stored in the collection configured by:

```kotlin
environment.config.tryGetString("db.mongo.collection.users") ?: "users"
```
You can customize this via environment configuration for different deployment environments.

## Additional Notes
- All timestamps use a `TimeStamp`  wrapper to map MongoDB's `$date`  format.
- The model includes optional nested objects (`PhysicalAttributes` , `ContactInfo` , `Preferences` , etc.), which will not be saved if null.
- Always sanitize and validate client data before calling `createUser()`  or `updateUserByUid()` .




<!--- Eraser file: https://app.eraser.io/workspace/KvOgWEvW6hYVdWV2GSHw --->