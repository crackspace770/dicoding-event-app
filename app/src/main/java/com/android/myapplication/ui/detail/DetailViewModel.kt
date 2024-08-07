package com.android.myapplication.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.android.myapplication.data.EventRepository
import com.android.myapplication.data.local.EventEntity
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: EventRepository):ViewModel() {

    private val eventData = MutableLiveData<EventEntity>()

    fun setEventData(event:EventEntity) {
        eventData.value = event
    }

    val bookmarkStatus = eventData.switchMap {
        repository.isEventBookmarked(it.name)
    }

    fun changeBookmark(eventDetail: EventEntity) {
        viewModelScope.launch {
            if (bookmarkStatus.value as Boolean) {
                repository.deleteEvent(eventDetail.name)
            } else {
                repository.saveEvent(eventDetail)
            }
        }
    }

}