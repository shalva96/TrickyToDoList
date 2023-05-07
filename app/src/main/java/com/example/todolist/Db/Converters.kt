package com.example.todolist.Db

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun stringToDate(value: String?): Date? {
        if (value == null) {
            return null
        }
        val dateFormat = SimpleDateFormat(Companion.DATE_FORMAT, Locale.getDefault())
        return dateFormat.parse(value)
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        if (date == null) {
            return null
        }
        val dateFormat = SimpleDateFormat(Companion.DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(date)
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
    }


}