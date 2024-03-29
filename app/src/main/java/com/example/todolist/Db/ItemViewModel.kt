package com.example.todolist.Db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Item>>
    val sortByDate: LiveData<List<Item>>
    val sortByColor: LiveData<List<Item>>
    val sortByDateAdded: LiveData<List<Item>>
    val unCheck: LiveData<List<Item>>
    val checked: LiveData<List<Item>>
    private val repository: Repository

    init {
        val getDao = MainDb.getDb(application)?.getDao()
        repository = getDao?.let { Repository(it) }!!
        readAllData = repository.readAllData
        sortByDate = repository.sortByDate
        sortByColor = repository.sortByColor
        sortByDateAdded = repository.sortByDateAdded
        unCheck = repository.unCheck
        checked = repository.checked

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

    fun itemCount(): Boolean {
        return repository.itemCount()
    }

    fun deleteSome(itemId: ArrayList<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSome(itemId)
        }
    }

    fun updateCheckboxForItem(itemId: Int, checkboxValue: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCheckboxForItem(itemId, checkboxValue)
        }
    }

    fun updateCheckboxBtnDone(itemId: ArrayList<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCheckboxBtnDone(itemId)
        }
    }

}