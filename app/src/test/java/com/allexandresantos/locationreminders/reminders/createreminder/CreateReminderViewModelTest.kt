package com.allexandresantos.locationreminders.reminders.createreminder
//
//import android.content.Context
//import android.os.Build
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.allexandresantos.locationreminders.reminders.ReminderDataItem
//import com.allexandresantos.locationreminders.reminders.data.FakeDataSource
//import com.allexandresantos.locationreminders.reminders.getOrAwaitValue
//import com.google.firebase.FirebaseApp
//import com.google.firebase.auth.FirebaseAuth
//
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.junit.Assert.*
//import org.junit.Rule
//import org.robolectric.annotation.Config
//
//@Config(sdk = [Build.VERSION_CODES.P])
//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//class CreateReminderViewModelTest {
//
//    private val firebaseAuth = FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext())
//
//    @get: Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//
//    val dataSource = FakeDataSource()
//
//    val createReminderViewModel = CreateReminderViewModel(ApplicationProvider.getApplicationContext(), dataSource)
//
//    //subjectUnderTest_actionOrInput_resultState
//    @Test
//    fun onClear_clearData_clearedData(){
//        createReminderViewModel.apply {
//            reminderTitle.value = "test"
//            reminderDescription.value = "test"
//            reminderSelectedLocationStr.value = "test"
//            latitude.value = .9
//            longitude.value = .4
//            reminderData = ReminderDataItem("test", "test", "test", .8, .7)
//        }
//
//        createReminderViewModel.apply {
//            reminderTitle.getOrAwaitValue()
//            reminderDescription.getOrAwaitValue()
//            reminderSelectedLocationStr.getOrAwaitValue()
//            latitude.getOrAwaitValue()
//            longitude.getOrAwaitValue()
//        }
//
//        createReminderViewModel.onClear()
//
//        assertEquals(createReminderViewModel.reminderTitle.value, null)
//        assertEquals(createReminderViewModel.reminderDescription.value, null)
//        assertEquals(createReminderViewModel.reminderSelectedLocationStr.value, null)
//        assertEquals(createReminderViewModel.latitude.value, null)
//        assertEquals(createReminderViewModel.longitude.value, null)
//        assertEquals(createReminderViewModel.reminderData, null)
//    }
//
//    @Test
//    fun reminder_getReminder_reminderDataObject(){
//        createReminderViewModel.reminderData = ReminderDataItem("test", "test", "test", .8, .7)
//
//        val result = createReminderViewModel.reminder()
//
//        assertEquals(createReminderViewModel.reminderData, result)
//    }
//
//}