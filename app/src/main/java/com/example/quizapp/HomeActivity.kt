package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.quizapp.databinding.ActivityHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.homeFragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.background = null
        binding.fab.setOnClickListener{
            val view: View = layoutInflater.inflate(R.layout.fab_item, null)
            val dialog = BottomSheetDialog(this)
            dialog.show()
            dialog.setContentView(view)
            val txtHocphan = view.findViewById<TextView>(R.id.txtHocPhan)
            txtHocphan.setOnClickListener {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            }
        }

    }

}