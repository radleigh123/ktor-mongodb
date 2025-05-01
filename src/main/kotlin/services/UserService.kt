package com.capstone.services

import com.capstone.model.User
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId

class UserService(private val database: MongoDatabase) {
    private val collection: MongoCollection<Document>

    init {
        database.createCollection("users")
        collection = database.getCollection("users")
    }

    suspend fun createUser(user: User): String = withContext(Dispatchers.IO) {
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
    }
}