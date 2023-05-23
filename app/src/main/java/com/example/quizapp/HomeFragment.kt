package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.adapter.QuizAdapter
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.model.Quiz
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        setUpFireStore()
        setUpRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomAppBar = activity?.findViewById<BottomAppBar>(R.id.bottomAppBar)
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        bottomAppBar?.visibility = View.VISIBLE
        fab?.visibility = View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpFireStore() {
        fireStore = FirebaseFirestore.getInstance()
        val collectionReference: CollectionReference = fireStore.collection("quizzes")
        collectionReference.addSnapshotListener{value, error ->
            if(value == null || error != null){
                Toast.makeText(requireContext(), "Error fetching data!", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
//            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(requireContext(), quizList)
        binding.hpRecyclerView.setHasFixedSize(true)
        binding.hpRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.hpRecyclerView.adapter = adapter
    }
}