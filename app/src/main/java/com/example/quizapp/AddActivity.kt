package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import com.example.quizapp.databinding.ActivityAddBinding
import com.example.quizapp.model.Question
import com.example.quizapp.model.Quiz
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class AddActivity : AppCompatActivity() {
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var binding: ActivityAddBinding
    private lateinit var title: String
    private var quesCount = 0
    private val listQues: MutableList<Question> = mutableListOf()
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fab.setOnClickListener {
            addQuestion()
            Log.d("quescount", quesCount.toString())
        }

        binding.button3.setOnClickListener {
            title = binding.txtTitle.text.toString()
            listQues.add(
                Question(
                    binding.edt1ques.text.toString(),
                    binding.edt1op1.text.toString(),
                    binding.edt1op2.text.toString(),
                    binding.edt1op3.text.toString(),
                    binding.edt1op4.text.toString(),
                    binding.edt1ans.text.toString()
                )
            )
            val allText: MutableList<EditText> = mutableListOf()

            for (i in 1..quesCount) {
                val edt = findViewById<EditText>(i)
                allText.add(edt)
            }

            for(i in 0 until count){
                val string1 = allText[i].text.toString()
                val string2 = allText[i+1].text.toString()
                val string3 = allText[i+2].text.toString()
                val string4 = allText[i+3].text.toString()
                val string5 = allText[i+4].text.toString()
                val string6 = allText[i+5].text.toString()
                val ques = Question(string1,string2,string3,string4,string5,string6)
                Log.d("QUES1", ques.toString())
                listQues.add(ques)
            }
        }
    }

    private fun addQuizFireStore(){
        fireStore = FirebaseFirestore.getInstance()
        val collectionReference: CollectionReference = fireStore.collection("quizzes")
        val newDocRef = collectionReference.document()

        val listkey: MutableList<String> = mutableListOf("question1", "question2")
        val map = listkey.associateWith { listQues[listkey.indexOf(it)] }
        val quiz = Quiz(id = collectionReference.id,
                        title = title,
                        questions = map as MutableMap<String, Question>
        )
        val mapQuiz = hashMapOf("questions" to quiz)
        newDocRef.set(mapQuiz)
            .addOnSuccessListener {
                // Thêm dữ liệu thành công
                Log.d("Thanh cong", "Quiz added with ID: ${newDocRef.id}")
            }
            .addOnFailureListener { e ->
                // Thêm dữ liệu thất bại
                Log.d("That bai", "Error adding quiz", e)
            }
    }

    private fun addQuestion() {
        count++
        quesCount++
        val ques = EditText(this)
        ques.id = quesCount
        binding.addAct.addView(ques)
        ques.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        ques.hint = "Câu hỏi"

        quesCount++
        val op1 = EditText(this)
        op1.id = quesCount
        binding.addAct.addView(op1)
        op1.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        op1.hint = "Phương án 1"

        quesCount++
        val op2 = EditText(this)
        op2.id = quesCount
        binding.addAct.addView(op2)
        op2.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        op2.hint = "Phương án 2"

        quesCount++
        val op3 = EditText(this)
        op3.id = quesCount
        binding.addAct.addView(op3)
        op3.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        op3.hint = "Phương án 3"

        quesCount++
        val op4 = EditText(this)
        op4.id = quesCount
        binding.addAct.addView(op4)
        op4.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        op4.hint = "Phương án 4"

        quesCount++
        val ans = EditText(this)
        ans.id = quesCount
        binding.addAct.addView(ans)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 80)
        ans.layoutParams = layoutParams
        ans.hint = "Câu trả lời"

    }
}