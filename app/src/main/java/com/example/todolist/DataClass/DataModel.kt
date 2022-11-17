package com.example.todolist.DataClass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Model for fragments

open class DataModel: ViewModel() {

    val openFragEmptyFragment: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}