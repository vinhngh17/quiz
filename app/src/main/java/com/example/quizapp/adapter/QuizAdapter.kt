package com.example.quizapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.HomeFragmentDirections
import com.example.quizapp.MainActivity
import com.example.quizapp.R
import com.example.quizapp.model.Quiz

class QuizAdapter(val context: Context, val quizzes: List<Quiz>):
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {
    inner class QuizViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var imgquiz: ImageView = itemView.findViewById(R.id.imgQuiz)
        var titleQuiz: TextView = itemView.findViewById(R.id.titleQuiz)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_img_item, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.titleQuiz.text = quizzes[position].title
        holder.itemView.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToQuestionFragment(
                title = quizzes[position].title)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }
}