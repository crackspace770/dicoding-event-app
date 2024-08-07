package com.android.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EventEntity::class],version = 1 )
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object{
        @Volatile
        private var INSTANCE: EventDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): EventDatabase{
            if (INSTANCE == null){
                synchronized(EventDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        EventDatabase::class.java, "favorite_db")
                        .build()
                }
            }
            return INSTANCE as EventDatabase
        }
    }

}