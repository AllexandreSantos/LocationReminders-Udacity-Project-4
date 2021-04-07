package com.allexandresantos.locationreminders.reminders.reminderslist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.allexandresantos.locationreminders.R
import com.allexandresantos.locationreminders.authentication.AuthenticationActivity
import com.allexandresantos.locationreminders.base.AuthenticationState
import com.allexandresantos.locationreminders.base.BaseFragment
import com.allexandresantos.locationreminders.base.NavigationCommand
import com.allexandresantos.locationreminders.databinding.FragmentRemindersBinding
import com.allexandresantos.locationreminders.utils.setDisplayHomeAsUpEnabled
import com.allexandresantos.locationreminders.utils.setTitle
import com.allexandresantos.locationreminders.utils.setup
import com.firebase.ui.auth.AuthUI
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReminderListFragment : BaseFragment() {
    //use Koin to retrieve the ViewModel instance
    override val _viewModel: RemindersListViewModel by viewModel()
    private lateinit var binding: FragmentRemindersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reminders, container, false)

        binding.viewModel = _viewModel

        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.app_name))

        binding.refreshLayout.setOnRefreshListener { _viewModel.loadReminders() }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {

        _viewModel.authenticationState.observe(viewLifecycleOwner){ authenticationState ->
            when(authenticationState){
                AuthenticationState.UNAUTHENTICATED -> logout()
                else -> Log.i(TAG, "observeViewModel: user is authenticated")
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        setupRecyclerView()
        binding.addReminderFAB.setOnClickListener {
            navigateToAddReminder()
        }
    }

    override fun onResume() {
        super.onResume()
        //load the reminders list on the ui
        _viewModel.loadReminders()
    }

    private fun navigateToAddReminder() {
        //use the navigationCommand live data to navigate between the fragments
        _viewModel.navigationCommand.postValue(
            NavigationCommand.To(
                ReminderListFragmentDirections.toCreateReminder()
            )
        )
    }

    private fun setupRecyclerView() {
        val adapter = RemindersListAdapter {
            _viewModel.navigationCommand.postValue(
                NavigationCommand.To(
                    ReminderListFragmentDirections.toDetailActivity(it)
                )
            )
        }

//        setup the recycler view using the extension function
        binding.reminderssRecyclerView.setup(adapter)
    }

    private fun logout() {
        val intent = Intent(context, AuthenticationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> AuthUI.getInstance().signOut(requireContext())
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        val TAG = ReminderListFragment::class.java.simpleName
    }

}