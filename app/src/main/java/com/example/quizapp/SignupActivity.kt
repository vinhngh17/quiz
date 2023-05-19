package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.quizapp.databinding.ActivitySignupBinding
import com.example.quizapp.model.User
import com.example.quizapp.model.saveUserToFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPass.text.toString()
            val repass = binding.edtRePass.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && repass.isNotEmpty()){
                if(pass == repass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if(it.isSuccessful){
                            val firebaseUser = firebaseAuth.currentUser
                            val firebaseEmail = firebaseUser?.email.toString()
                            if (firebaseUser != null) {

                                // Tạo đối tượng User
                                val user = User(firebaseUser.uid, firebaseUser.email!!, firebaseEmail.substringBefore("@"))

                                // Lưu dữ liệu người dùng vào Firestore
                                saveUserToFirestore(user)
                            }
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Nhập thiếu thông tin!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}