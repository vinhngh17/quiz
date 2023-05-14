package com.example.quizapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.model.Question
import com.example.quizapp.model.Quiz
import com.google.firebase.firestore.FirebaseFirestore

class QuestionViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    private val _curQues = MutableLiveData<Question>()
    val curQues: LiveData<Question>
        get() = _curQues

    private var _score = 0
    val score: Int
        get() = _score

    private var curWordCount = 0

    private lateinit var _correctAns: String
    val correctAns: String
        get() = _correctAns

    // Cac bien ho tro
    private var quesList: MutableList<Question> = mutableListOf()
    var count = 0


    private val _quizzes = MutableLiveData<List<Quiz>>()
    val quizzes: MutableLiveData<List<Quiz>>
        get() = _quizzes

    private val _listQuestionData = MutableLiveData<List<Question>>()
    val listQuestionData: MutableLiveData<List<Question>>
        get() = _listQuestionData

    fun setUpFireStore(title: String) {
        firestore.collection("quizzes")
            .whereEqualTo("title", title)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val dataQuiz = querySnapshot.toObjects(Quiz::class.java)
                    val dataQues = dataQuiz[0].questions
                    val listDataQues = dataQues.values.toMutableList()
                    listQuestionData.postValue(listDataQues)
                    _quizzes.postValue(dataQuiz)
                    Log.d("Data in vm", listDataQues.toString() )
                }
            }
    }

    fun getNextQues(){
        if(listQuestionData.value!!.isNotEmpty()){
            _curQues.value = listQuestionData.value!!.get(count)
            Log.d("DATA curques", _curQues.value.toString())
            _correctAns = _curQues.value!!.correctAns
            if (quesList.contains(_curQues.value)) {
                getNextQues()
                count++
            } else {
                curWordCount++
                count++
                quesList.add(_curQues.value!!)
            }
            Log.d("count in vm", count.toString())
            Log.d("quesList in vm", quesList.toString())
        }else return
        Log.d("correct ans in vm", correctAns)
    }

    fun nextQues(): Boolean{
        return if (curWordCount < listQuestionData.value!!.size){
            Log.d("DATA out vm listquestions", listQuestionData.value.toString())
            getNextQues()
            true
        } else false
    }

    fun isCorrect(curUserAns: String): Boolean{
        if(curUserAns.equals(_correctAns)){
            _score++
            return true
        }
        return false
    }


}