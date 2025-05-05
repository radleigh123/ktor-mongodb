package com.capstone.di

import com.capstone.controller.UserController
import com.capstone.model.User
import com.capstone.repository.UserRepository
import com.capstone.services.UserService
import com.mongodb.client.MongoDatabase
import org.koin.dsl.module

val userModule = module {
    single<MongoDatabase> {
        connectToMongoDB()
    }
    single { get<MongoDatabase>().getCollection("users", User::class.java) }
    single { UserRepository(get()) }
    single { UserService(get()) }
    single { UserController(get()) }
}
