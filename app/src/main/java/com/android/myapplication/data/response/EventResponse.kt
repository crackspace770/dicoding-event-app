package com.android.myapplication.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventResponse (
    val error: Boolean? = null,
    val message: String? = null,
    val listEvents: List<ListEvent>? = null
):Parcelable

@Parcelize
data class ListEvent (
    val id: Long? = null,
    val name: String? = null,
    val summary: String? = null,
    val description: String? = null,
    val imageLogo: String? = null,
    val mediaCover: String? = null,
    val category: String? = null,
    val ownerName: String? = null,
    val cityName: String? = null,
    val quota: Long? = null,
    val registrants: Long? = null,
    val beginTime: String? = null,
    val endTime: String? = null,
    val link: String? = null
):Parcelable
