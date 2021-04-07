package com.allexandresantos.locationreminders.reminders.data

import com.allexandresantos.locationreminders.data.ReminderDataSource
import com.allexandresantos.locationreminders.data.dto.ReminderDTO
import com.allexandresantos.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var reminders: MutableList<ReminderDTO>? = mutableListOf()) : ReminderDataSource {

    private var shouldReturnError = false

    fun setReturnError(value: Boolean){
        shouldReturnError = value
    }

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        if (shouldReturnError){
            return Result.Error(fakeDataSourceErrorMessage)
        }
        reminders?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(fakeDataSourceErrorMessage)
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders?.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        reminders?.let { reminders ->
            reminders.firstOrNull { it.id == id }?.let { reminder ->
            return Result.Success(reminder)
            }
        }

        return Result.Error("Reminder not found")
    }

    override suspend fun deleteAllReminders() {
        reminders?.clear()
    }

    companion object{
        const val fakeDataSourceErrorMessage = "Reminders not found"
    }

}