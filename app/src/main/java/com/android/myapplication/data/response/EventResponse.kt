package com.android.myapplication.data.response

import android.os.Parcelable
import com.android.myapplication.data.local.EventEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventResponse (
    val error: Boolean? = null,
    val message: String? = null,
    val listEvents: List<EventEntity>? = null
):Parcelable


