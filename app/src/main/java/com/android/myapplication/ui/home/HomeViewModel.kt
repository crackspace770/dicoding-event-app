package com.android.myapplication.ui.home

import androidx.lifecycle.ViewModel
import com.android.myapplication.data.EventRepository

class HomeViewModel(private val repository: EventRepository):ViewModel() {

    fun getUpcomingEvents() = repository.eventActive()

    fun getAllEvents() = repository.allEvent()

}