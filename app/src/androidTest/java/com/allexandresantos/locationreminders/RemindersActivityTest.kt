package com.allexandresantos.locationreminders

//import android.app.Application
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.filters.LargeTest
//import com.allexandresantos.locationreminders.data.ReminderDataSource
//import com.allexandresantos.locationreminders.data.local.LocalDB
//import com.allexandresantos.locationreminders.data.local.RemindersLocalRepository
//import com.allexandresantos.locationreminders.reminders.reminderslist.RemindersListViewModel
//import com.allexandresantos.locationreminders.reminders.createreminder.CreateReminderViewModel
//import kotlinx.coroutines.runBlocking
//import org.junit.Before
//import org.junit.runner.RunWith
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.core.context.startKoin
//import org.koin.core.context.stopKoin
//import org.koin.dsl.module
//import org.koin.test.AutoCloseKoinTest
//import org.koin.test.get
//
//@RunWith(AndroidJUnit4::class)
//@LargeTest
////END TO END test to black box test the app
//class RemindersActivityTest :
//    AutoCloseKoinTest() {// Extended Koin Test - embed autoclose @after method to close Koin after every test
//
//    private lateinit var repository: ReminderDataSource
//    private lateinit var appContext: Application
//
//    /**
//     * As we use Koin as a Service Locator Library to develop our code, we'll also use Koin to test our code.
//     * at this step we will initialize Koin related code to be able to use it in out testing.
//     */
//    @Before
//    fun init() {
//        stopKoin()//stop the original app koin
//        appContext = getApplicationContext()
//        val myModule = module {
//            viewModel {
//                RemindersListViewModel(
//                    appContext,
//                    get() as ReminderDataSource
//                )
//            }
//            single {
//                CreateReminderViewModel(
//                    appContext,
//                    get() as ReminderDataSource
//                )
//            }
//            single { RemindersLocalRepository(get()) as ReminderDataSource }
//            single { LocalDB.createRemindersDao(appContext) }
//        }
//        //declare a new koin module
//        startKoin {
//            modules(listOf(myModule))
//        }
//        //Get our real repository
//        repository = get()
//
//        //clear the data to start fresh
//        runBlocking {
//            repository.deleteAllReminders()
//        }
//    }
//

//    TODO: add End to End testing to the app
//
//}
