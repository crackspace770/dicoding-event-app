package com.android.myapplication.data.api

import com.android.myapplication.data.response.EventResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("events?active=-1")
    suspend fun getSearch(
        @Query("q") name: String
    ): Response<EventResponse>

    @GET("events?active=0")
    suspend fun getFinishedEvent(): Response<EventResponse>

    @GET("events?active=1")
    suspend fun getActiveEvent(): Response<EventResponse>

    @GET("events?active=-1")
    suspend fun getAllEvents():Response<EventResponse>

}