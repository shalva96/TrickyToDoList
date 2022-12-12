package com.example.todolist.DataClass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.Db.Item
import com.example.todolist.R
import com.example.todolist.UpdateFragment
import java.util.concurrent.Flow

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



    val recyclerViewItemId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val recyclerViewItem: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val recyclerViewItemDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }






}