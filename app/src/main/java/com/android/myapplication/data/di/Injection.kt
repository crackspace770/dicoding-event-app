package com.android.myapplication.data.di

import com.android.myapplication.data.EventRepository
import com.android.myapplication.data.api.ApiConfig

object Injection {

    fun provideRepository(): EventRepository {
        val apiService = ApiConfig.provideApiService()

        return EventRepository(apiService)
    }

}