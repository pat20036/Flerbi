package com.pat.flerbi.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.pat.flerbi.databinding.FragmentDarkThemeBinding
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DarkThemeFragment : Fragment() {
    private lateinit var binding: FragmentDarkThemeBinding
    private val userViewModel by sharedViewModel<UserViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDarkThemeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        observeDarkTheme()
        binding.darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
            userViewModel.darkTheme(isChecked)
        }

        binding.darkThemeBackButton.setOnClickListener()
        {
            findNavController().popBackStack()
        }
    }

    private fun observeDarkTheme()
    {
        userViewModel.darkTheme.observe(viewLifecycleOwner, Observer {
            if(it) binding.darkThemeSwitch.isChecked = true
        })
    }
}