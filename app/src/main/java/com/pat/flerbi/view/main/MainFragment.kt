package com.pat.flerbi.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentMainBinding
import com.pat.flerbi.helpers.QueueInfo.nick
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val userViewModel by sharedViewModel<UserViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        userViewModel.isUserActive()
        userViewModel.getProfilePoints()
        userViewModel.getProfileAchievements()
        userViewModel.getProfileRecommends()
        userViewModel.getUserNickname()
        userViewModel.getActiveUsersCount()
        userViewModel.getLastLocation()
        userViewModel.getFavoriteLocation()

        observeUserStats()
        observeUserStatus()
        observeActiveUsersCount()
        observeLastLocation()
        observeFavoriteLocation()

        nick = userViewModel.userNickname.value.toString()
        binding.nicknameTextView.text = userViewModel.userNickname.value
        binding.statsNicknameTextView.text = userViewModel.userNickname.value

        binding.favLocationItem.setOnClickListener()
        {
            findNavController().navigate(R.id.action_mainFragment_to_locationSettingsFragment)
        }

        binding.locationEditText.setOnClickListener()
        {
            val extras = FragmentNavigatorExtras(
                binding.locationEditText to "locationEditText",
                binding.locationCardView to "locationCardView"
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_queueFragment,
                null,
                null,
                extras
            )
        }
    }

    private fun observeActiveUsersCount() {
        userViewModel.usersCount.observe(viewLifecycleOwner, Observer {
            binding.activeUsersTextView.text = it
        })
    }

    private fun observeUserStatus() {
        userViewModel.isUserActive.observe(viewLifecycleOwner, Observer {
            if (it) binding.connectionStatusImage.setImageResource(R.drawable.online_circle)
            else binding.connectionStatusImage.setImageResource(R.drawable.offline_circle)
        })
    }

    private fun observeUserStats() {
        userViewModel.profilePoints.observe(viewLifecycleOwner, Observer {
            binding.profilePointsProgressBar.visibility = View.INVISIBLE
            binding.accountPointsTextView.text = it
        })

        userViewModel.profileRecommends.observe(viewLifecycleOwner, Observer {
            binding.accountRecommendsTextView.text = it
        })

        userViewModel.profileAchievements.observe(viewLifecycleOwner, Observer {
            binding.accountAchivementsTextView.text = it
        })
    }

    private fun observeLastLocation() {
        userViewModel.userLastLocation.observe(viewLifecycleOwner, Observer {
            binding.lastLocationTextView.text = it
        })
    }

    private fun observeFavoriteLocation() {
        userViewModel.userFavoriteLocation.observe(viewLifecycleOwner, Observer {
            binding.favLocationTextView.text = it
        })
    }

}