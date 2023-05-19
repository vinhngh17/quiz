package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.adapter.QuizAdapter
import com.example.quizapp.databinding.FragmentSearchBinding
import com.example.quizapp.model.Quiz
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    private lateinit var fireStore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpFireStore()
        setUpRecyclerView()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    val filterList = mutableListOf<Quiz>()
                    for(i in quizList){
                        if(i.title.lowercase(Locale.ROOT).contains(newText)){
                            filterList.add(i)
                        }
                    }

                    if(filterList.isEmpty()){
                        Toast.makeText(context, "Không có dữ liệu", Toast.LENGTH_SHORT).show()
                    } else{
                        adapter.setFilterList(filterList)
                    }
                }
                return true
            }
        })
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
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(requireContext(), quizList)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
}