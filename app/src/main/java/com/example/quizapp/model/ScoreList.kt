package com.example.quizapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScoreList(val list: ArrayList<String>): Parcelable
