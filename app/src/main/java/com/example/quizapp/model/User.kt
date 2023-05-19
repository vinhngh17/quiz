package com.example.quizapp.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

data class User(var uId: String = "",
                var uEmail: String = "",
                var uName: String = "",
                var quizNames: MutableMap<String, Int> = mutableMapOf() )

    fun saveUserToFirestore(user: User) {
        val db = FirebaseFirestore.getInstance()
        val usersRef = db.collection("users").document()
            .set(user)
            .addOnSuccessListener {
                Log.d("Firestore", "User added with ID: ${user.uId}")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error adding user", e)
            }
    }
