package com.example.todolist.DataClass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Model for fragments

open class DataModel: ViewModel() {

    val openFragEmptyFragment: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val newTaskFromHomePage: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val backFromAddPage: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }


}