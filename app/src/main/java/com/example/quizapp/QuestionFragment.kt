package com.example.quizapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.databinding.FragmentQuestionBinding
import com.example.quizapp.viewmodel.QuestionViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore

class QuestionFragment : Fragment() {
    private lateinit var viewModel: QuestionViewModel
    private lateinit var binding: FragmentQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments?.let {
//            title = it.getString("title").toString()
//            Log.d("TITLE", title)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        viewModel.quizzes.observe(viewLifecycleOwner, Observer { quizzes ->
            // Use the quizzes data here
            Log.d("DATA in frag", quizzes.toString())
        })

        viewModel.listQuestionData.observe(viewLifecycleOwner, Observer { listQuestionData ->
            // Use the quizzes data here
            Log.d("DATA in frag", listQuestionData.toString())
            binding.txtQuestion.text = listQuestionData.get(0).ques
        })

        viewModel.setUpFireStore("Tieu de lop 1")
        viewModel.getNextQues()
        Log.d("DATA out frag", viewModel.quizzes.value.toString())
//        binding.btnNext.setOnClickListener { onNextQues() }
//        updateQuesOnScreen()
    }

    private fun updateQuesOnScreen(){
        binding.txtQuestion.text = viewModel.curQues.ques
        binding.rad1.text = viewModel.curQues.op1
        binding.rad2.text = viewModel.curQues.op2
        binding.rad3.text = viewModel.curQues.op3
        binding.rad4.text = viewModel.curQues.op4
    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Congratulation!")
            .setMessage("You scored " + viewModel.score)
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.restart)) { _, _ ->
                restartGame()
            }
            .show()
    }

    private fun onNextQues(){
        var userAns = ""
        when{
            binding.rad1.isChecked -> userAns = binding.rad1.text.toString()
            binding.rad2.isChecked -> userAns = binding.rad2.text.toString()
            binding.rad3.isChecked -> userAns = binding.rad3.text.toString()
            binding.rad4.isChecked -> userAns = binding.rad4.text.toString()
        }

        Log.d("Fragment", "User ans: $userAns")
        Log.d("Fragment", "Count: "+ viewModel.count.toString())

        if(viewModel.isCorrect(userAns)){
            Log.d("Fragment", "True ans: " + viewModel.correctAns)
            if(viewModel.nextQues()){
                updateQuesOnScreen()
            }
            else { showFinalScoreDialog() }

        } else{
            Log.d("Fragment", "True ans: " + viewModel.correctAns)
            if(viewModel.nextQues()){
                updateQuesOnScreen()
            }
            else { showFinalScoreDialog() }
        }
    }

    private fun restartGame() {
        TODO("Not yet implemented")
    }

    private fun exitGame() {
//        findNavController().navigate(R.id.action_questionFragment_to_homeFragment)
    }

}