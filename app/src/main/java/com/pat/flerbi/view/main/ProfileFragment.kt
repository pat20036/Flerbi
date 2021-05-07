package com.pat.flerbi.view.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentProfileBinding
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val userViewModel by sharedViewModel<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        userViewModel.getUserEmail()
        binding.nicknameProfileTextView.text = userViewModel.userNickname.value
        binding.emailProfileTextView.text = userViewModel.userEmail.value

        binding.logoutButton.setOnClickListener()
        {
            userViewModel.logoutUser()
        }
        binding.darkThemeButton.setOnClickListener()
        {
            findNavController().navigate(R.id.action_profileFragment_to_darkThemeFragment)
        }
        binding.moreSettingButton.setOnClickListener()
        {
           findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }

    }
}