package com.capstone.repository

import com.capstone.model.User
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId

class UserRepository(
    private val collection: MongoCollection<User>
) {
    suspend fun create(user: User): String = withContext(Dispatchers.IO) {
        collection.insertOne(user)
        return@withContext user.id
    }

/*
    suspend fun getAllUsers(): List<User> = withContext(Dispatchers.IO) {
        collection.find().map { User.fromDocument(it) }.toList()
    }
*/

    suspend fun read(id: String): User? = withContext(Dispatchers.IO) {
//        collection.find(Filters.eq("_id", ObjectId(id))).first()?.let(User::fromDocument)
        collection.find(
            Filters.eq("_id", ObjectId(id))
        ).firstOrNull()
    }

    suspend fun update(id: String, user: User): User? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(
            Filters.eq("_id", ObjectId(id)), user
        )
    }

    suspend fun delete(id: String): User? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(
            Filters.eq("_id", ObjectId(id))
        )
    }
}