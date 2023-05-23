package com.example.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentPersonalBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class PersonalFragment : Fragment(R.layout.fragment_personal) {
    private lateinit var binding : FragmentPersonalBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.txtEmail.text = firebaseAuth.currentUser?.email
        binding.txtLogout.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("ĐĂNG XUẤT")
            .setMessage("Bạn có muốn đăng xuất ?")
            .setCancelable(true)
            .setNegativeButton("Không") { _, _ ->
            }
            .setPositiveButton("Có") { _, _ ->
                logOut()
            }
            .show()
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        findNavController().navigate(R.id.action_personalFragment_to_loginActivity)
    }
}