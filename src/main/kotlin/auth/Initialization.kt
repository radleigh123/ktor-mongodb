package com.capstone.auth

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.File
import java.io.FileInputStream

// TODO: Duplicate Firebase initialization code
fun initFirebase() {
    /*val serviceAccount = FileInputStream(System.getenv("AUTH_FIREBASE_SERVICE_KEY"))
    // TODO: REMOVE THIS LINE AFTER TESTING
    println("Service Account: ${System.getenv("AUTH_FIREBASE_SERVICE_KEY")}")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()

    if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options)
    }*/

/*
    // Only initialize if Firebase hasn't been initialized yet
    if (FirebaseApp.getApps().isEmpty()) {
        try {
            val serviceAccount = FileInputStream(System.getenv("AUTH_FIREBASE_SERVICE_KEY"))

            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()

            FirebaseApp.initializeApp(options)
            println("Firebase successfully initialized")
        } catch (e: Exception) {
            println("Firebase initialization error: ${e.message}")
            e.printStackTrace()
        }
    } else {
        println("Firebase already initialized, skipping initialization")
    }
*/
}