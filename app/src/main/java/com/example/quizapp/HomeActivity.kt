package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.quizapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNagivationView.setOnClickListener { replaceFragment() }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragHome = HomeFragment()
        val fragPerson = PersonalFragment()
        val fragFolder = FolderFragment()
        val fragSearch = SearchFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragHome)
            commit()
        }


    }
}