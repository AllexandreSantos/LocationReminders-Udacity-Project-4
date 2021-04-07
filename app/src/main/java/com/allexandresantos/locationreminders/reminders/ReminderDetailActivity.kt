package com.allexandresantos.locationreminders.reminders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.allexandresantos.locationreminders.R
import com.allexandresantos.locationreminders.databinding.ActivityReminderDetailBinding

/**
 * Activity that displays the reminder details after the user clicks on the notification
 */
class ReminderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderDetailBinding

    private val args: ReminderDetailActivityArgs? by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reminder_detail)
        initViews()

    }

    private fun initViews() {

        args?.reminderDataItem?.let {
            binding.reminderDataItem = it
        }

        intent?.getSerializableExtra(EXTRA_ReminderDataItem)?.let {
            binding.reminderDataItem = it as ReminderDataItem?
        }

    }

    companion object {
        private const val EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem"

        fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
            val intent = Intent(context, ReminderDetailActivity::class.java)
            intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
            return intent
        }
    }
}
