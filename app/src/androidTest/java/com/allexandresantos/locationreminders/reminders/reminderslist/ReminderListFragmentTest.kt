package com.allexandresantos.locationreminders.reminders.reminderslist

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.allexandresantos.locationreminders.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
//UI Testing
@MediumTest
class ReminderListFragmentTest {

//    TODO: test the displayed data on the UI.
//    TODO: add testing for the error messages.

//    @ExperimentalCoroutinesApi
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()

//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()

//    @Rule
//    @JvmField
//    var mActivityTestRule = ActivityTestRule(RemindersActivity::class.java)

    @Test
    fun testNavigationToCreateReminderScreen() {

        // GIVEN - On the remindersList screen
        val reminderListScenario = launchFragmentInContainer<ReminderListFragment>(null, R.style.AppTheme)
        val navController = Mockito.mock(NavController::class.java)
        reminderListScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // WHEN - Click on the "+" button
        onView(withId(R.id.addReminderFAB)).perform(click())

        // THEN - Verify that we navigate to the create reminder screen
        Mockito.verify(navController).navigate(ReminderListFragmentDirections.toCreateReminder())

    }

}