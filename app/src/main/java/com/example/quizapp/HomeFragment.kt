package com.example.quizapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizapp.adapter.QuizAdapter
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.model.Quiz
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    private lateinit var fireStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setUpFireStore()
        setUpRecyclerView()
    }

    private fun setUpFireStore() {
        fireStore = FirebaseFirestore.getInstance()
        val collectionReference: CollectionReference = fireStore.collection("quizzes")
        collectionReference.addSnapshotListener{value, error ->
            if(value == null || error != null){
                Toast.makeText(requireContext(), "Error fetching data!", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setData() {
        quizList.add(Quiz("25/4/2023","Ten Lop"))
        quizList.add(Quiz("25/4/2023","Ten Lop"))
        quizList.add(Quiz("25/4/2023","Ten Lop"))
        quizList.add(Quiz("25/4/2023","Ten Lop"))

    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(requireContext(), quizList)
        binding.hpRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.hpRecyclerView.adapter = adapter
    }
}