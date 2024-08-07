package com.android.myapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {

    @Query("SELECT * FROM evententity")
    fun getBookmarkedEvent(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveEvent(news: EventEntity)

    @Query("DELETE FROM evententity WHERE name = :eventTitle")
    suspend fun deleteEvent(eventTitle: String)

    @Query("SELECT EXISTS(SELECT * FROM evententity WHERE name = :name)")
    fun isEventBookmarked(name: String): LiveData<Boolean>

}