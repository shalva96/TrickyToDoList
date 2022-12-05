package com.example.todolist.Db

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class Repository(private val getDao: Dao) {

    val readAllData: LiveData<List<Item>> = getDao.getAllItems()

    suspend fun addItem(item: Item) {
        getDao.insertItem(item)
    }

}