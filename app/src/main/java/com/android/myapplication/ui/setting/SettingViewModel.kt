package com.android.myapplication.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.android.myapplication.data.preference.SettingPreference
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreference):ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun getNotificationSettings(): LiveData<Boolean> { // New method for notification
        return pref.getNotificationSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun saveNotificationSetting(isNotificationActive: Boolean) { // New method for notification
        viewModelScope.launch {
            pref.saveNotificationSetting(isNotificationActive)
        }
    }

}