package com.example.todolist.Db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: Item)

    @Query("SELECT * FROM items")
    fun getAllItems(): LiveData<List<Item>>

    @Update(entity = Item::class)
    suspend fun update(item: Item)


    @Delete(entity = Item::class)
    fun delete(item: Item)

}