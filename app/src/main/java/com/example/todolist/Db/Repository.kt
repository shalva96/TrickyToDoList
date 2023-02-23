package com.example.todolist.Db

import android.widget.CompoundButton
import androidx.lifecycle.LiveData

class Repository(private val getDao: Dao) {

    val readAllData: LiveData<List<Item>> = getDao.getAllItems()
    val unCheck: LiveData<List<Item>> = getDao.unCheck()
    val checked: LiveData<List<Item>> = getDao.checked()


    suspend fun addItem(item: Item) {
        getDao.insertItem(item)
    }

    suspend fun update(item: Item) {
        getDao.update(item)
    }

    suspend fun delete(item: Item) {
        getDao.delete(item)
    }

    suspend fun deleteSome(itemId: List<Int?>) {
        getDao.deleteSome(itemId)
    }

    suspend fun updateCheckboxForItem(itemId: Int, checkboxValue: Boolean) {
        getDao.updateCheckboxForItem(itemId, checkboxValue)
    }

}