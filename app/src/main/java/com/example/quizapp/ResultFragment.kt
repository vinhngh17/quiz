package com.example.quizapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.databinding.FragmentQuestionBinding
import com.example.quizapp.databinding.FragmentResultBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ResultFragment : Fragment(R.layout.fragment_result) {
    private val args: ResultFragmentArgs by navArgs()
    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("listScore", args.listScore.toString())

        val mutableScore: MutableList<String> = args.listScore.list.toMutableList()
        Log.d("listScore", mutableScore.toString())

        val listAdapter: MutableList<String> = mutableListOf()
        var count = 1
        for(i in mutableScore){
            listAdapter.add("Câu ${count}: " + i)
            count++
        }
        binding.txtScore.text = "Điểm của bạn: ${args.score}"
        binding.listView.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_activated_1, listAdapter)
        binding.button.setOnClickListener {
            val action = ResultFragmentDirections.actionResultFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

}