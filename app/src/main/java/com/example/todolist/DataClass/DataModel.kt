package com.example.todolist.DataClass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {

    val openFragEmptyFragment: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

}