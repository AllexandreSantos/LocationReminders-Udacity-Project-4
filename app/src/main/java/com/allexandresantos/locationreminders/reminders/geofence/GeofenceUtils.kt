package com.allexandresantos.locationreminders.reminders.geofence

import com.allexandresantos.locationreminders.BuildConfig
import java.util.concurrent.TimeUnit


data class LandmarkDataObject(val title: String?, val description: String?, val location: String?, val latitude: Double?, val longitude: Double?, val id: String)

object GeofencingConstants {
    val GEOFENCE_EXPIRATION_IN_MILLISECONDS: Long = TimeUnit.HOURS.toMillis(1)
    const val GEOFENCE_RADIUS_IN_METERS = 50f
    const val ACTION_GEOFENCE_EVENT = BuildConfig.APPLICATION_ID + "action.ACTION_GEOFENCE_EVENT"
}