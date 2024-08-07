package com.android.myapplication.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.android.myapplication.data.api.ApiService
import com.android.myapplication.data.local.EventDao
import com.android.myapplication.data.local.EventEntity

class EventRepository(
    private val apiService: ApiService,
    private val eventDao:EventDao
) {

    fun searchEvent(query: String): LiveData<Result<List<EventEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getSearch(query)
            if (response.isSuccessful) {
                val search = response.body()?.listEvents ?: emptyList()
                emit(Result.Success(search))
            } else {
                emit(Result.Error("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("EventRepository", "searchEvent: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun eventActive(): LiveData<Result<List<EventEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getActiveEvent()
            if (response.isSuccessful) {
                val events = response.body()?.listEvents ?: emptyList()
                emit(Result.Success(events))
            } else {
                emit(Result.Error("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("EventRepository", "eventActive: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun allEvent(): LiveData<Result<List<EventEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllEvents()
            if (response.isSuccessful) {
                val finishedEvents = response.body()?.listEvents ?: emptyList()
                emit(Result.Success(finishedEvents))
            } else {
                emit(Result.Error("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("EventRepository", "eventAll: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getBookmarkedEvent(): LiveData<List<EventEntity>> {
        return eventDao.getBookmarkedEvent()
    }

    suspend fun saveEvent(event: EventEntity) {
        eventDao.saveEvent(event)
    }

    suspend fun deleteEvent(title: String) {
        eventDao.deleteEvent(title)
    }

    fun isEventBookmarked(title: String): LiveData<Boolean> {
        return eventDao.isEventBookmarked(title)
    }




}
