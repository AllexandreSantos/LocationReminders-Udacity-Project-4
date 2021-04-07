package com.allexandresantos.locationreminders.authentication

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.allexandresantos.locationreminders.base.BaseViewModel
import com.allexandresantos.locationreminders.utils.Event

class AuthenticationViewModel(val app: Application) : BaseViewModel(app) {

    val action = MutableLiveData<Event<AuthenticationAction>>()

    fun setLaunchSignInFlow(){
        action.value = Event(AuthenticationAction.LaunchSignInFlow)
    }

}

sealed class AuthenticationAction{
    object LaunchSignInFlow : AuthenticationAction()
}

