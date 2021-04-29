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
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val userViewModel by viewModel<UserViewModel>()
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
        observeUserStats()
        observeUserStatus()

        binding.nicknameTextView.text =  userViewModel.userNickname.value
        binding.statsNicknameTextView.text =  userViewModel.userNickname.value

        binding.locationEditText.setOnClickListener()
        {
            val extras = FragmentNavigatorExtras(binding.locationEditText to "search2", binding.cardView3 to "search")
            findNavController().navigate(
                R.id.action_mainFragment_to_queueFragment,
                null,
                null,
                extras
            )
        }
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

}