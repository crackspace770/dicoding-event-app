package com.android.myapplication.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val NOTIFICATION_KEY = booleanPreferencesKey("notification_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    fun getNotificationSetting(): Flow<Boolean> { // New method for notification
        return dataStore.data.map { preferences ->
            preferences[NOTIFICATION_KEY] ?: true // Default to true if not set
        }
    }

    suspend fun saveNotificationSetting(isNotificationActive: Boolean) { // New method for notification
        dataStore.edit { preferences ->
            preferences[NOTIFICATION_KEY] = isNotificationActive
        }
    }


    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}