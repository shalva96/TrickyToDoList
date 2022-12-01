package com.example.todolist.sharedPref

import android.content.Context
import com.example.todolist.Constants.KEY
import com.example.todolist.Constants.KEY_DB
import com.example.todolist.Constants.PREF_NAME
import com.example.todolist.Constants.PREF_NAME_DB

class SharedPref(context: Context) {

    private val myPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = myPref.edit()


    private val myPrefDB = context.getSharedPreferences(PREF_NAME_DB, Context.MODE_PRIVATE)
    private val editorDB = myPrefDB.edit()

    fun saveValue(value: Boolean) {
        editor.putBoolean(KEY, value)
        editor.apply()
    }

    fun getValue(): Boolean {
        return myPref.getBoolean(KEY, false)
    }

    fun saveValueDB(value: Boolean) {
        editorDB.putBoolean(KEY_DB, value)
        editorDB.apply()
    }

    fun getValueDB(): Boolean {
        return myPrefDB.getBoolean(KEY_DB, false)
    }

}