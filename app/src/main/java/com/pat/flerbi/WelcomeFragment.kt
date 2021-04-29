package com.pat.flerbi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pat.flerbi.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
private lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        binding.loginBt.setOnClickListener()
        {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
        binding.registerBt.setOnClickListener()
        {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
    }

}