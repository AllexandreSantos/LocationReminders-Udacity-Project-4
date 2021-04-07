package com.allexandresantos.locationreminders.reminders

import com.allexandresantos.locationreminders.data.dto.ReminderDTO
import com.allexandresantos.locationreminders.reminders.geofence.LandmarkDataObject
import java.io.Serializable
import java.util.*

/**
 * data class acts as a data mapper between the DB and the UI
 */
data class ReminderDataItem(
    var title: String?,
    var description: String?,
    var location: String?,
    var latitude: Double?,
    var longitude: Double?,
    val id: String = UUID.randomUUID().toString()
) : Serializable

fun ReminderDataItem.toLandMark(): LandmarkDataObject {
    return LandmarkDataObject(
        title = this.title,
        description = this.description,
        location = this.location,
        latitude = this.latitude,
        longitude =  this.longitude,
        id = this.id
    )
}