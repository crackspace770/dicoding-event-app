package com.android.myapplication.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.android.myapplication.data.EventRepository
import com.android.myapplication.data.api.ApiConfig
import com.android.myapplication.data.local.EventDatabase
import com.android.myapplication.data.preference.SettingPreference

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
object Injection {

    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.provideApiService()
        val dao =   EventDatabase.getDatabase(context).eventDao()
        return EventRepository(apiService,dao)
    }

    fun provideSettingPreference(context: Context): SettingPreference {
        return SettingPreference.getInstance(context.dataStore)
    }

}