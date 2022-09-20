package com.example.sociallogin.Roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [userModel::class], version = 2)

abstract class database:RoomDatabase() {

    companion object {
        private var Database: database? = null
        private val DATABASE_NAME = "userDatabase"


        @Synchronized
        fun getinstence(context: Context): database {
            if (Database == null) {
                Database = Room.databaseBuilder(
                    context.applicationContext , database::class.java ,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return Database!!
        }
    }
    abstract fun  userDao():Dao
}