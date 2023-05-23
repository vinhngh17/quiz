package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        firebaseAuth = FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser == null) {
            Handler().postDelayed({
                startActivity(Intent(this, StartActivity::class.java))
            }, 2000)
        }else{
            Handler().postDelayed({
                startActivity(Intent(this, HomeActivity::class.java))
            }, 2000)
        }
    }
}