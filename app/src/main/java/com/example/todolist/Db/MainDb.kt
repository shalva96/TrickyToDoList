package com.example.todolist.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Item::class], version = 1, exportSchema = false)
abstract class MainDb: RoomDatabase() {

    abstract fun getDao(): Dao


//    companion object{
//        fun getDb(context: Context): MainDb{
//            return Room.databaseBuilder(
//                context.applicationContext,
//                MainDb::class.java,
//                "todo.db"
//            ).build()
//        }
//    }

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
                ).build()
                INSTANCE = instance
                return instance
            }
        }


    }






}

