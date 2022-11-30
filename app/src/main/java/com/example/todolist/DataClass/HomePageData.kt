package com.example.todolist.DataClass

import java.io.Serializable

data class HomePageData(

    val checkbox: Boolean,
    val description: String,
    val color: Int,
    val date: String

):Serializable
