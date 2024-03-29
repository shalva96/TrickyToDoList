package com.example.todolist.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database (entities = [Item::class], version = 5, exportSchema = false)
abstract class MainDb: RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{

        @Volatile
        private var INSTANCE: MainDb? = null

        fun getDb(context: Context): MainDb? {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDb::class.java,
                    "todo.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }


    }






}

