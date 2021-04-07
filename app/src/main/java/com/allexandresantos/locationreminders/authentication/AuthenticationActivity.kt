package com.allexandresantos.locationreminders.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.allexandresantos.locationreminders.R
import com.allexandresantos.locationreminders.base.AuthenticationState
import com.allexandresantos.locationreminders.databinding.ActivityAuthenticationBinding
import com.allexandresantos.locationreminders.reminders.RemindersActivity

class AuthenticationActivity : AppCompatActivity() {

    private val viewModel: AuthenticationViewModel by lazy {
        ViewModelProvider(this).get(AuthenticationViewModel::class.java)
    }

    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.authenticationState.observe(this){ authenticationState ->
            when(authenticationState){
                AuthenticationState.AUTHENTICATED -> navigateToReminders()
                else -> Log.d(TAG, "observeViewModel: user is not authenticated")
            }
        }

        viewModel.action.observe(this){
            it.getContentIfNotHandled().let{ action ->
                when(action){
                    is AuthenticationAction.LaunchSignInFlow -> launchSignInFlow()
                }
            }
        }

    }

    private fun launchSignInFlow() {
        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setLockOrientation(true)
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.fui_ic_googleg_color_24dp)
                        .setTheme(R.style.AppTheme)
                        .build(),
            SIGN_IN_RESULT_CODE
        )
    }

    private fun navigateToReminders() {
        startActivity(Intent(this, RemindersActivity::class.java))
        finish()
    }

    companion object{
        const val SIGN_IN_RESULT_CODE = 1001
        val TAG = AuthenticationState::class.java.simpleName
    }
}
