package com.allexandresantos.locationreminders.reminders.createreminder

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.allexandresantos.locationreminders.R
import com.allexandresantos.locationreminders.base.BaseFragment
import com.allexandresantos.locationreminders.databinding.FragmentCreateReminderBinding
import com.allexandresantos.locationreminders.reminders.geofence.GeofenceBroadcastReceiver
import com.allexandresantos.locationreminders.reminders.geofence.GeofencingConstants.ACTION_GEOFENCE_EVENT
import com.allexandresantos.locationreminders.reminders.geofence.GeofencingConstants.GEOFENCE_EXPIRATION_IN_MILLISECONDS
import com.allexandresantos.locationreminders.reminders.geofence.GeofencingConstants.GEOFENCE_RADIUS_IN_METERS
import com.allexandresantos.locationreminders.reminders.toLandMark
import com.allexandresantos.locationreminders.utils.setDisplayHomeAsUpEnabled
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.inject

class CreateReminderFragment : BaseFragment() {
    /**
    * Getting the viewmodel as a single is not really necessary, I've done it here for future reference
    * of how to implement a single using Koin (a shared view model using the activity as a owner of the view model would suffice,
    * or yet we could make another view model for the selectReminderLocation and save the data to a database or sharedPref)
    */

    //Get the view model this time as a single to be shared with the another fragment
    override val _viewModel: CreateReminderViewModel by inject()

    private lateinit var binding: FragmentCreateReminderBinding

    private lateinit var geofencingClient: GeofencingClient

    // A PendingIntent for the Broadcast Receiver that handles geofence transitions.
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireActivity(), GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT

        PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_reminder, container, false)

        setDisplayHomeAsUpEnabled(true)

        geofencingClient = LocationServices.getGeofencingClient(requireActivity())

        binding.viewModel = _viewModel

        observeViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        binding.saveReminder.setOnClickListener {
            _viewModel.validateReminder()
        }
    }

    private fun observeViewModel() {
        _viewModel.action.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { action ->
                when (action) {
                    is CreateReminderViewModel.CreateReminderAction.AddGeoFence -> addGeofence()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun addGeofence() {

        val currentGeofenceData = _viewModel.reminder()!!.toLandMark()

        // Build the Geofence Object
        val geofence: Geofence = Geofence.Builder().run{
            currentGeofenceData.let{
                // Set the request ID, string to identify the geofence.
                this.setRequestId(it.id)
                    // Set the circular region of this geofence.
                    .setCircularRegion(it.latitude!!, it.longitude!!, GEOFENCE_RADIUS_IN_METERS)
                    // Set the expiration duration of the geofence. This geofence gets
                    // automatically removed after this period of time.
                    .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    // Set the transition types of interest. Alerts are only generated for these
                    // transition. We track entry and exit transitions in this sample.
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            }
            this.build()
        }

        // Build the geofence request
        val geofencingRequest = GeofencingRequest.Builder()
            // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
            // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
            // is already inside that geofence.
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)

            // Add the geofences to be monitored by geofencing service.
            .addGeofence(geofence)
            .build()

        geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
            addOnSuccessListener {
                Log.d(TAG,"Added Geofence: " + geofence.requestId)
                _viewModel.reminder()?.let { _viewModel.saveReminder(it)}
            }
            addOnFailureListener {
                _viewModel.geofenceError()
                if ((it.message != null)) {
                    Log.e(TAG, it.message.toString())
                }
            }
        }
    }

    companion object{
        val TAG = CreateReminderFragment::class.java.simpleName
    }

}
