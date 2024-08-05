package com.android.myapplication.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.myapplication.data.EventRepository
import com.android.myapplication.data.di.Injection
import com.android.myapplication.ui.active.ActiveViewModel
import com.android.myapplication.ui.finished.FinishedViewModel
import com.android.myapplication.ui.home.HomeViewModel

class ViewModelFactory constructor(private val repository: EventRepository) :
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
            else -> throw  IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }
    }

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository()).also { instance = it }
            }
        }
    }
}