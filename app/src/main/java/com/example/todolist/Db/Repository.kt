package com.example.todolist.Db

import androidx.lifecycle.LiveData

class Repository(private val getDao: Dao) {

    val readAllData: LiveData<List<Item>> = getDao.getAllItems()

    suspend fun addItem(item: Item) {
        getDao.insertItem(item)
    }

    suspend fun update(item: Item) {
        getDao.update(item)
    }

    suspend fun delete(item: Item) {
        getDao.delete(item)
    }

}