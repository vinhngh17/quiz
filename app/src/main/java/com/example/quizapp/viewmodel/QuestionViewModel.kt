package com.example.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.data.allQuestion
import com.example.quizapp.model.Question

class QuestionViewModel : ViewModel() {
    private lateinit var _curQues: Question
//    private val _curQues = MutableLiveData<Question>()
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

    init {
        getNextQues()
    }

    fun getNextQues(){
        _curQues = allQuestion.get(count)
        _correctAns = _curQues.correctAns
        if (quesList.contains(_curQues)){
            getNextQues()
            count++
        } else {
            curWordCount++
            count++
            quesList.add(_curQues)
        }
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