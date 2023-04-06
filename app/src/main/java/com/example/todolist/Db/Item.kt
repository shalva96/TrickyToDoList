package com.example.todolist.Db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity (tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "checkbox")
    var checked: Boolean,
    @ColumnInfo(name = "texts")
    var text: String,
    @ColumnInfo(name = "colors")
    var color: Int,
    @ColumnInfo(name = "dates")
    var date: String?
)
