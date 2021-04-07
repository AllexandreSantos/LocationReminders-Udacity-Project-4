package com.allexandresantos.locationreminders.reminders.reminderslist

import com.allexandresantos.locationreminders.R
import com.allexandresantos.locationreminders.base.BaseRecyclerViewAdapter
import com.allexandresantos.locationreminders.reminders.ReminderDataItem


//Use data binding to show the reminder on the item
class RemindersListAdapter(callBack: (selectedReminder: ReminderDataItem) -> Unit) :
    BaseRecyclerViewAdapter<ReminderDataItem>(callBack) {
    override fun getLayoutRes(viewType: Int) = R.layout.it_reminder
}
