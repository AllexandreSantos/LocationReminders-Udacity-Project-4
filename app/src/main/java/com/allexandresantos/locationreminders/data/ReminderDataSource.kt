package com.allexandresantos.locationreminders.data

import com.allexandresantos.locationreminders.data.dto.ReminderDTO
import com.allexandresantos.locationreminders.data.dto.Result

/**
 * Main entry point for accessing reminders data.
 */
interface ReminderDataSource {
    suspend fun getReminders(): Result<List<ReminderDTO>>
    suspend fun getReminder(id: String): Result<ReminderDTO>
    suspend fun saveReminder(reminder: ReminderDTO)
    suspend fun deleteAllReminders()
}