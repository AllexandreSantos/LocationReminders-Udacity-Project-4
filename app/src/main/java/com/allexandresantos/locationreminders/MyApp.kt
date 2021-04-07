package com.allexandresantos.locationreminders

import android.app.Application
import com.allexandresantos.locationreminders.data.ReminderDataSource
import com.allexandresantos.locationreminders.data.local.LocalDB
import com.allexandresantos.locationreminders.data.local.RemindersLocalRepository
import com.allexandresantos.locationreminders.reminders.reminderslist.RemindersListViewModel
import com.allexandresantos.locationreminders.reminders.createreminder.CreateReminderViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * use Koin Library as a service locator
         */
        val myModule = module {
            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel {
                RemindersListViewModel(
                    get(),
                    get() as ReminderDataSource
                )
            }
            //Declare singleton definitions to be later injected using by inject()
            single {
                //This view model is declared singleton to be used across multiple fragments
                CreateReminderViewModel(
                    get(),
                    get() as ReminderDataSource
                )
            }
            single { RemindersLocalRepository(get()) as ReminderDataSource }
            single { LocalDB.createRemindersDao(this@MyApp) }
        }

        startKoin {
            androidContext(this@MyApp)
            modules(listOf(myModule))
        }
    }
}