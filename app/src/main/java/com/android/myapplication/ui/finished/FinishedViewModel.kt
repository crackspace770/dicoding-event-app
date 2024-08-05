package com.android.myapplication.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.android.myapplication.data.EventRepository
import com.android.myapplication.data.response.ListEvent
import com.android.myapplication.data.Result

class FinishedViewModel(private val repository: EventRepository):ViewModel() {

    private val searchQuery = MutableLiveData<String>()


    fun setSearchQuery(query: String) {
        this.searchQuery.value = query
    }

    val searchResult: LiveData<Result<List<ListEvent>>> = searchQuery.switchMap { query ->
        repository.searchEvent(query)
    }



}