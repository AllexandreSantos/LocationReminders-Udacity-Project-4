package com.allexandresantos.locationreminders.reminders.reminderslist

import android.os.Build
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.allexandresantos.locationreminders.data.dto.ReminderDTO
import com.allexandresantos.locationreminders.reminders.MainCoroutineRule
import com.allexandresantos.locationreminders.reminders.data.FakeDataSource
import com.allexandresantos.locationreminders.reminders.data.FakeDataSource.Companion.fakeDataSourceErrorMessage
import com.allexandresantos.locationreminders.reminders.getOrAwaitValue
import com.google.firebase.FirebaseApp
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {

    private lateinit var remindersListViewModel: RemindersListViewModel

    private lateinit var dataSource: FakeDataSource

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        val firebaseAuth = FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext())

        val reminder1 = ReminderDTO("Title1", "Description1", "Location1", .1, .1)
        val reminder2 = ReminderDTO("Title2", "Description2", "Location2", .2, .2)
        val reminder3 = ReminderDTO("Title3", "Description3", "Location3", .3, .3)

        val reminders = listOf(reminder1, reminder2, reminder3)

        dataSource = FakeDataSource(reminders = reminders.toMutableList())

        remindersListViewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(), dataSource)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    //subjectUnderTest_actionOrInput_resultState
    @Test
    fun loadReminders_getRemindersList_remindersListNotEmpty(){

        remindersListViewModel.loadReminders()

        val list = remindersListViewModel.remindersList.getOrAwaitValue()

        assertThat(list, `is`(not(emptyList())))

        val noData = remindersListViewModel.showNoData.getOrAwaitValue()

        assertThat(noData, `is`(false))

    }

    //As per requirement in the rubric
    @Test
    fun loadReminders_loading(){

        mainCoroutineRule.pauseDispatcher()

        remindersListViewModel.loadReminders()

        assertThat(remindersListViewModel.showLoading.getOrAwaitValue(), `is`(true))

        mainCoroutineRule.resumeDispatcher()

        assertThat(remindersListViewModel.showLoading.getOrAwaitValue(), `is`(false))

    }

    //As per requirement in the rubric
    @Test
    fun loadRemindersWhenRemindersAreUnavailable_callErrorToDisplay(){
            dataSource.setReturnError(true)

            remindersListViewModel.loadReminders()

            val errorMessage = remindersListViewModel.showSnackBar.getOrAwaitValue()

            assertThat(errorMessage, `is`(fakeDataSourceErrorMessage))
    }

}