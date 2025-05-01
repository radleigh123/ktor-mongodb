package com.capstone.auth

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.File
import java.io.FileInputStream

fun initFirebase() {
//    val serviceAccount = FileInputStream("src/main/resources/firebase-service-account.json")
    val serviceAccount = FileInputStream("src/main/kotlin/auth/easeplan-7594d-firebase-adminsdk-fbsvc-49ed1b6a7b.json")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()

    if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options)
    }
}