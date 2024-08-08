package com.android.myapplication.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.datastore.core.IOException
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.myapplication.R
import com.android.myapplication.data.local.EventEntity
import com.android.myapplication.data.response.EventResponse
import com.android.myapplication.ui.detail.DetailActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        private val TAG = NotificationWorker::class.java.simpleName
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "Dicoding Event"
        const val EVENT_API_URL = "https://event-api.dicoding.dev/events?active=1&limit=1"
        const val EXTRA_EVENT = "extra_event"
    }

    override fun doWork(): Result {
        return getUpcomingEvent()
    }

    private fun getUpcomingEvent(): Result {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(EVENT_API_URL)
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                responseBody?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val adapter = moshi.adapter(EventResponse::class.java)
                    val eventResponse = adapter.fromJson(it)
                    eventResponse?.let { response ->
                        if (!response.listEvents.isNullOrEmpty()) {
                            val event = response.listEvents[0]
                            showNotification(event) // Pass the event object here
                            Result.success()
                        } else {
                            Log.d(TAG, "No upcoming events found")
                            Result.success()
                        }
                    } ?: run {
                        Log.e(TAG, "Failed to parse response")
                        Result.failure()
                    }
                } ?: run {
                    Log.e(TAG, "Empty response body")
                    Result.failure()
                }
            } else {
                Log.e(TAG, "Failed to fetch events: ${response.code}")
                Result.failure()
            }
        } catch (e: IOException) {
            Log.e(TAG, "Exception while fetching events", e)
            Result.failure()
        }
    }

    private fun showNotification(event: EventEntity) {
        val intent = Intent(applicationContext, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_EVENT, event)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Upcoming event: ${event.name}")
            .setContentText("Will begin at ${event.beginTime}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent) // Set the pending intent here
            .setAutoCancel(true) // Automatically cancel the notification when tapped
            .build()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}