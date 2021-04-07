package com.allexandresantos.locationreminders.reminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.allexandresantos.locationreminders.data.dto.ReminderDTO
import com.allexandresantos.locationreminders.data.dto.Result
import com.allexandresantos.locationreminders.data.dto.succeeded
import com.allexandresantos.locationreminders.data.local.RemindersDatabase
import com.allexandresantos.locationreminders.data.local.RemindersLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Medium Test to test the repository
@MediumTest
class RemindersLocalRepositoryTest {

    private lateinit var remindersLocalRepository: RemindersLocalRepository
    private lateinit var database: RemindersDatabase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        // using an in-memory database for testing, since it doesn't survive killing the process
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        remindersLocalRepository =
            RemindersLocalRepository(
                database.reminderDao(),
                Dispatchers.Main
            )
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun saveReminder_retrievesReminder() = runBlocking{
            // GIVEN - a new task saved in the database
            val reminder = ReminderDTO("title", "description", "location", 0.1, 0.1)
            remindersLocalRepository.saveReminder(reminder)

            // WHEN  - Task retrieved by ID
            val result = remindersLocalRepository.getReminder(reminder.id)

            // THEN - Same task is returned
            assertThat(result.succeeded, `is`(true))
            result as Result.Success
            assertThat(result.data.title, `is`(reminder.title))
            assertThat(result.data.description, `is`(reminder.description))
            assertThat(result.data.location, `is`(reminder.location))
            assertThat(result.data.longitude, `is`(reminder.longitude))
            assertThat(result.data.latitude, `is`(reminder.latitude))
            assertThat(result.data.id, `is`(reminder.id))
    }

    @Test
    fun loadRemindersWhenRemindersAreUnavailable_callResultError() = runBlocking{
        val fakeId = "fake_id_!!!"
        val result = remindersLocalRepository.getReminder(fakeId)

        assertThat(result.succeeded, `is`(false))
    }

}