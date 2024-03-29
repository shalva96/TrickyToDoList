package com.example.todolist.Db

import androidx.room.*
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
    @ColumnInfo(name = "sortDates")
    var sortByDate: String,
    @ColumnInfo(name = "dates")
    var date: String
)
