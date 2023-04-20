package com.example.todolist.Db

import androidx.lifecycle.LiveData

class Repository(private val getDao: Dao) {

    val readAllData: LiveData<List<Item>> = getDao.readAllData()
    val sortByDate: LiveData<List<Item>> = getDao.sortByDate()
    val sortByColor: LiveData<List<Item>> = getDao.sortByColor()
    val sortByDateAdded: LiveData<List<Item>> = getDao.sortByDateAdded()
    val unCheck: LiveData<List<Item>> = getDao.unCheck()
    val checked: LiveData<List<Item>> = getDao.checked()


    suspend fun addItem(item: Item) {
        getDao.insertItem(item)
    }

    fun itemCount(): Boolean {
        return getDao.itemCount()
    }
    suspend fun update(item: Item) {
        getDao.update(item)
    }

    suspend fun delete(item: Item) {
        getDao.delete(item)
    }

    suspend fun deleteSome(itemId: ArrayList<Int>) {
        getDao.deleteSome(itemId)
    }

    suspend fun updateCheckboxForItem(itemId: Int, checkboxValue: Boolean) {
        getDao.updateCheckboxForItem(itemId, checkboxValue)
    }

}