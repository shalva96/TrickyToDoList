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
    val saveAndBackFromAddPage: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val updateFragment: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }


    // get value for update!!
    val recyclerViewItemId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val recyclerViewItemText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val recyclerViewItemDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


}

