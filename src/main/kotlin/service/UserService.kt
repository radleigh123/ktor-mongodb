package com.capstone.services

import com.capstone.model.User
import com.capstone.repository.UserRepository

class UserService(
    private val userRepository: UserRepository
) {
    // Redundant
    /*private val collection: MongoCollection<Document>

    init {
        database.createCollection("users")
        collection = database.getCollection("users")
    }*/

    // Redundant 'UserRepository' has already been created
    /*suspend fun createUser(user: User): String = withContext(Dispatchers.IO) {
        val doc = user.toDocument()
        collection.insertOne(doc)
        doc["_id"].toString()
    }

    suspend fun getUserById(id: String): User? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("_id", ObjectId(id))).first()?.let(User::fromDocument)
    }

    suspend fun updateUser(id: String, user: User): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), user.toDocument())
    }

    suspend fun deleteUser(id: String): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("_id", ObjectId(id)))
    }*/

    suspend fun createUser(user: User) = userRepository.create(user)

    suspend fun getUserById(id: String) = userRepository.read(id)

    suspend fun updateUser(id: String, user: User) = userRepository.update(id, user)

    suspend fun deleteUser(id: String) = userRepository.delete(id)

    suspend fun getAllUsers() = userRepository.getAllUsers()

    suspend fun getUserByEmail(email: String) = userRepository.getUserByEmail(email)

    suspend fun updateUserByUid(uid: String, user: User) = userRepository.updateUserByUid(uid, user)
}