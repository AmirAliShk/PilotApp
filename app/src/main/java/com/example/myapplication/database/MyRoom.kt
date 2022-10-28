package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.app.MyApplication

// TODO when you change the entities structure, please increase the version of dataBase.
@Database(
    entities = [LocationsTable::class],
    version = 1
)
abstract class MyRoom : RoomDatabase() {
    abstract fun locationsDao(): LocationsDao

    companion object {
        @Volatile
        private var INSTANCE: MyRoom? = null

        fun getDatabase(context: Context): MyRoom {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    MyApplication.context, MyRoom::class.java, "PilotAppDataBase"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}