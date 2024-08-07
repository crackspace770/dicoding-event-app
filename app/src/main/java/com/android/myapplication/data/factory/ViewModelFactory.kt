package com.android.myapplication.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.myapplication.data.EventRepository
import com.android.myapplication.data.di.Injection
import com.android.myapplication.ui.active.ActiveViewModel
import com.android.myapplication.ui.detail.DetailViewModel
import com.android.myapplication.ui.finished.FinishedViewModel
import com.android.myapplication.ui.home.HomeViewModel
import com.android.myapplication.data.preference.SettingPreference
import com.android.myapplication.ui.setting.SettingViewModel

class ViewModelFactory(private val repository: EventRepository, private val pref: SettingPreference) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ActiveViewModel::class.java) -> {
                ActiveViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FinishedViewModel::class.java) -> {
                FinishedViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }
    }

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                val repository = Injection.provideRepository(context)
                val pref = Injection.provideSettingPreference(context)
                instance ?: ViewModelFactory(repository, pref).also { instance = it }
            }
        }
    }
}
