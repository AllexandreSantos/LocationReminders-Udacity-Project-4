package com.allexandresantos.locationreminders.reminders.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.allexandresantos.locationreminders.reminders.geofence.GeofencingConstants.ACTION_GEOFENCE_EVENT
import com.allexandresantos.locationreminders.utils.errorMessage
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

/**
 * Triggered by the Geofence.  Since we can have many Geofences at once, we pull the request
 * ID from the first Geofence, and locate it within the cached data in our Room DB
 *
 * Or users can add the reminders and then close the app, So our app has to run in the background
 * and handle the geofencing in the background.
 * To do that you can use https://developer.android.com/reference/android/support/v4/app/JobIntentService to do that.
 *
 */

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("oi", "onReceive: recebeu aqui")

        if(intent.action == ACTION_GEOFENCE_EVENT){
            val geofencingEvent = GeofencingEvent.fromIntent(intent)

            if(geofencingEvent.hasError()){
                val errorMessage = errorMessage(context, geofencingEvent.errorCode)
                Log.e(TAG, errorMessage)
                return
            }

            if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
                GeofenceTransitionsJobIntentService.enqueueWork(context, intent)
            }
        }

    }

    companion object{
        val TAG = GeofenceBroadcastReceiver::class.java.simpleName
    }
}