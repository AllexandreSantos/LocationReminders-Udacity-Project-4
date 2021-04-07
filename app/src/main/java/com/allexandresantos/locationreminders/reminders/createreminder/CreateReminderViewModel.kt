package com.allexandresantos.locationreminders.reminders.createreminder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.allexandresantos.locationreminders.R
import com.allexandresantos.locationreminders.base.BaseViewModel
import com.allexandresantos.locationreminders.base.NavigationCommand
import com.allexandresantos.locationreminders.data.ReminderDataSource
import com.allexandresantos.locationreminders.data.dto.ReminderDTO
import com.allexandresantos.locationreminders.reminders.ReminderDataItem
import com.allexandresantos.locationreminders.utils.Event
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import kotlinx.coroutines.launch

class CreateReminderViewModel(val app: Application, val dataSource: ReminderDataSource) :
    BaseViewModel(app) {
    val reminderTitle = MutableLiveData<String?>()
    val reminderDescription = MutableLiveData<String?>()
    val reminderSelectedLocationStr = MutableLiveData<String?>()
    val selectedPOI = MutableLiveData<PointOfInterest?>()
    val latitude = MutableLiveData<Double?>()
    val longitude = MutableLiveData<Double?>()
    var reminderData: ReminderDataItem? = null
    val action = MutableLiveData<Event<CreateReminderAction>>()

    /**
     * Clear the live data objects to start fresh next time the view model gets called
     */
    fun onClear() {
        reminderTitle.value = null
        reminderDescription.value = null
        reminderSelectedLocationStr.value = null
        selectedPOI.value = null
        latitude.value = null
        longitude.value = null
        reminderData = null
    }

    fun reminder() = reminderData

    fun validateReminder() {
        reminderData = ReminderDataItem(
            reminderTitle.value,
            reminderDescription.value,
            reminderSelectedLocationStr.value,
            latitude.value,
            longitude.value
        )

        if (validateEnteredData(reminderData)) {
            action.value = Event(CreateReminderAction.AddGeoFence)
        }

    }

    private fun validateEnteredData(reminderData: ReminderDataItem?): Boolean {
        if (reminderData?.title.isNullOrEmpty()) {
            showSnackBarInt.value = R.string.err_enter_title
            return false
        }

        if (reminderData?.location.isNullOrEmpty()) {
            showSnackBarInt.value = R.string.err_select_location
            return false
        }
        return true
    }

    fun saveReminder(reminderData: ReminderDataItem) {
        showLoading.value = true

        if (!validateEnteredData(reminderData)) return

        viewModelScope.launch {
            dataSource.saveReminder(
                ReminderDTO(
                    reminderData.title,
                    reminderData.description,
                    reminderData.location,
                    reminderData.latitude,
                    reminderData.longitude,
                    reminderData.id
                )
            )

            showLoading.value = false
            showToast.value = app.getString(R.string.reminder_saved)
            navigationCommand.value = NavigationCommand.Back
            onClear()
        }
    }

    fun navigateToSelectLocation(){
        navigationCommand.value = NavigationCommand.To(CreateReminderFragmentDirections.actionCreateReminderFragmentToSelectLocationFragment())
    }

    fun validateLocation() {
        if (longitude.value != null || latitude.value != null) {
            navigationCommand.value = NavigationCommand.Back
            showToast.value = app.getString(R.string.location_saved)
        } else showToast.value = app.getString(R.string.no_location)
    }

    fun setLocation(latLng: LatLng?) {
        latLng?.latitude.let { latitude.value = it }
        latLng?.longitude.let { longitude.value = it }
        reminderSelectedLocationStr.value = latLng.toString()
    }

    fun setPOI(pointOfInterest: PointOfInterest) {
        selectedPOI.value = pointOfInterest
        selectedPOI.value?.latLng?.latitude.let { latitude.value = it }
        selectedPOI.value?.latLng?.longitude.let { longitude.value = it }
        selectedPOI.value?.name.let { reminderSelectedLocationStr.value = it }
    }

    fun geofenceError(){
        showSnackBarInt.value = R.string.error_adding_geofence
    }

    sealed class CreateReminderAction{
        object AddGeoFence: CreateReminderAction()
    }

}