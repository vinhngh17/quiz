package com.example.quizapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.model.Question
import com.example.quizapp.data.allQuestion
import com.example.quizapp.model.Quiz
import com.google.firebase.firestore.FirebaseFirestore

class QuestionViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var _curQues: Question

    val curQues: Question
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

//    init {
//        setUpFireStore()
//        getNextQues()
//    }

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
                    _quizzes.postValue(dataQuiz)
                    Log.d("Data in vm", listDataQues.toString() )
                    _listQuestionData.postValue(listDataQues)
                }
            }
    }

    fun getNextQues(){
        Log.d("DATA out vm", quizzes.value.toString())
        Log.d("DATA out vm list", listQuestionData.value.toString())
        if(listQuestionData.value != null) {
            _curQues = listQuestionData.value!!.get(count)
            _correctAns = _curQues.correctAns
            if (quesList.contains(_curQues)) {
                getNextQues()
                count++
            } else {
                curWordCount++
                count++
                quesList.add(_curQues)
            }
        }else return
    }

    fun nextQues(): Boolean{
        return if (curWordCount < allQuestion.size){
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