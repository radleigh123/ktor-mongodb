package com.capstone.repository

import com.capstone.model.User
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId

class UserRepository(
    private val collection: MongoCollection<Document>
) {
    suspend fun create(user: User): String = withContext(Dispatchers.IO) {
        val doc = user.toDocument()
        collection.insertOne(doc)
        doc["_id"].toString()
    }

    suspend fun read(id: String): User? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("_id", ObjectId(id))).first()?.let(User::fromDocument)
    }

    suspend fun update(id: String, user: User): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), user.toDocument())
    }

    suspend fun delete(id: String): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("_id", ObjectId(id)))
    }

    suspend fun getAllUsers(): List<User> = withContext(Dispatchers.IO) {
        collection.find().map { User.fromDocument(it) }.toList()
    }

    suspend fun getUserByEmail(email: String): User? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("email", email)).first()?.let(User::fromDocument)
    }

    suspend fun updateUserByUid(uid: String, user: User): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("userId", uid), user.toDocument())
    }
}