package com.example.quizapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentQuestionBinding
import com.example.quizapp.model.ScoreList
import com.example.quizapp.viewmodel.QuestionViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QuestionFragment : Fragment() {
    private lateinit var viewModel: QuestionViewModel
    private lateinit var binding: FragmentQuestionBinding
    private lateinit var title: String
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            title = it.getString("title").toString()
            Log.d("TITLE", title)
        }
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

        viewModel.setUpFireStore(title)
        viewModel.listQuestionData.observe(viewLifecycleOwner, Observer { listQuestionData ->
            // Use the quizzes data here
            viewModel.getNextQues()
            Log.d("DATA in frag", listQuestionData.toString())
        })

        viewModel.curQues.observe(viewLifecycleOwner, Observer { curQues ->
            Log.d("curQues frag", curQues.toString())
            binding.txtQuestion.text = curQues.ques
            binding.rad1.text = curQues.op1
            binding.rad2.text = curQues.op2
            binding.rad3.text = curQues.op3
            binding.rad4.text = curQues.op4
            binding.txtWordCount.text = getString(R.string.txt_word_count, viewModel.count + 1, viewModel.listQuestionData.value!!.size)
        })

        binding.btnNext.setOnClickListener { onNextQues() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomAppBar = activity?.findViewById<BottomAppBar>(R.id.bottomAppBar)
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        bottomAppBar?.visibility = View.GONE
        fab?.visibility = View.GONE
    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Congratulation!")
            .setMessage("You scored " + viewModel.score)
            .setCancelable(false)
            .setNegativeButton("Tiếp theo") { _, _ ->
                next()
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

        if(viewModel.isCorrect(userAns)){
            Log.d("Fragment", "True ans: " + viewModel.correctAns)
            if(!viewModel.nextQues()){
                showFinalScoreDialog()
                addScoreToFireStore(viewModel.score)
            }

        } else{
            if(!viewModel.nextQues()){
                showFinalScoreDialog()
                addScoreToFireStore(viewModel.score)
            }
        }
    }


    private fun next() {
        val listScore = ScoreList(viewModel._allScore)
        val action = QuestionFragmentDirections.actionQuestionFragmentToResultFragment(
            listScore = listScore, score = viewModel.score
        )
        findNavController().navigate(action)
    }


    fun addScoreToFireStore(score: Int) {
        val db = Firebase.firestore
        val collectionRef = db.collection("users").document(currentUser!!.uid)
        val titleQuiz = title

        collectionRef.update("quizNames.$titleQuiz", score)
            .addOnSuccessListener {
                Log.d("TAG", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error updating document", e)
            }
    }


}