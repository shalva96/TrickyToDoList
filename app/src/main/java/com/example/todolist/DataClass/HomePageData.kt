package com.example.todolist.DataClass

import android.widget.CheckBox
import java.io.Serializable

data class HomePageData(

    val checkbox: Boolean,
    val description: String,
    val color: String,
    val date: String

):Serializable
