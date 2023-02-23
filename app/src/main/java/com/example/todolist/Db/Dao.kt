package com.example.todolist.Db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: Item)

    @Query("SELECT * FROM items")
    fun getAllItems(): LiveData<List<Item>>

    @Query("SELECT * FROM items WHERE checkbox is 0")
    fun unCheck(): LiveData<List<Item>>

    @Query("SELECT * FROM items WHERE checkbox is 1")
    fun checked(): LiveData<List<Item>>

    @Update(entity = Item::class)
    suspend fun update(item: Item)

    @Query("UPDATE items SET checkbox = :checkboxValue WHERE id is :itemId")
    suspend fun updateCheckboxForItem(itemId: Int, checkboxValue: Boolean)

    @Delete(entity = Item::class)
    suspend fun delete(item: Item)

    @Query("DELETE FROM items WHERE id like :itemId")
    suspend fun deleteSome(itemId: List<Int?>)

}