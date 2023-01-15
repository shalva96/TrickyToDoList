package com.example.todolist.Db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Item>>
    private val repository: Repository

    init {
        val getDao = MainDb.getDb(application)?.getDao()
        repository = getDao?.let { Repository(it) }!!
        readAllData = repository.readAllData
    }

    fun addItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(item)
        }
    }

    fun delete(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(item)
        }
    }

}