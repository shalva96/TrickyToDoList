package com.example.todolist.sharedPref

import android.content.Context
import com.example.todolist.Constants.KEY
import com.example.todolist.Constants.PREF_NAME

class SharedPref(context: Context) {

    private val myPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = myPref.edit()

    fun saveValue(value: Boolean) {
        editor.putBoolean(KEY, value)
        editor.apply()
    }

    fun getValue(): Boolean {
        return myPref.getBoolean(KEY, false)
    }


}