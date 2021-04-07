package com.allexandresantos.locationreminders.reminders.geofence

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.allexandresantos.locationreminders.data.ReminderDataSource
import com.google.android.gms.location.Geofence
import com.allexandresantos.locationreminders.data.dto.ReminderDTO
import com.allexandresantos.locationreminders.data.dto.Result
import com.allexandresantos.locationreminders.reminders.ReminderDataItem
import com.allexandresantos.locationreminders.utils.sendNotification
import com.google.android.gms.location.GeofencingEvent
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

class GeofenceTransitionsJobIntentService : JobIntentService(), CoroutineScope {

    val remindersLocalRepository: ReminderDataSource by inject()

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob


    companion object {
        private const val JOB_ID = 573
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, GeofenceTransitionsJobIntentService::class.java, JOB_ID, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        Log.d("oi", "onHandleWork: handling work")
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        sendNotification(geofencingEvent.triggeringGeofences)
    }

    private fun sendNotification(triggeringGeofences: List<Geofence>) {


        for (i in triggeringGeofences.indices) {

            Log.d("oi", "sendNotification: " + triggeringGeofences [i].requestId)
            val requestId = triggeringGeofences[i].requestId

//        Interaction to the repository has to be through a coroutine scope
            CoroutineScope(coroutineContext).launch(SupervisorJob()) {
                //get the reminder with the request id
                val result = remindersLocalRepository.getReminder(requestId)
                if (result is Result.Success<ReminderDTO>) {
                    val reminderDTO = result.data
                    //send a notification to the user with the reminder details
                    sendNotification(
                        this@GeofenceTransitionsJobIntentService, ReminderDataItem(
                            reminderDTO.title,
                            reminderDTO.description,
                            reminderDTO.location,
                            reminderDTO.latitude,
                            reminderDTO.longitude,
                            reminderDTO.id)
                    )
                }
            }
        }
    }

}