package com.capstone.auth

import com.google.firebase.auth.FirebaseAuth

suspend fun verifyToken(idToken: String): String? {
    return try {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        decodedToken.uid // Return the user ID (Firebase UID)
    } catch (e: Exception) {
        null // Token verification failed
    }
}