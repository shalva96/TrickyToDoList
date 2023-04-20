package com.example.todolist.DataClass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.Db.Item

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
    // fragments end!!!

// get value for update!
    val recyclerViewItemId: MutableLiveData<Item> by lazy {
        MutableLiveData<Item>()
    }

}

