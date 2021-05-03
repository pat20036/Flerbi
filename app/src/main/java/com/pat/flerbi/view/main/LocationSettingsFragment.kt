package com.pat.flerbi.view.main

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentLocationSettingsBinding
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class LocationSettingsFragment : Fragment() {
    private lateinit var binding: FragmentLocationSettingsBinding
    private val userViewModel by sharedViewModel<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        observeFavoriteLocation()
        binding.assignLocationButton.setOnClickListener()
        {
            val location = binding.favoriteLocationEditText.text.toString()
            userViewModel.setFavoriteLocation(location)
            Snackbar.make(requireView(), "Saved!", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun observeFavoriteLocation()
    {
        userViewModel.userFavoriteLocation.observe(viewLifecycleOwner, Observer {
            binding.favoriteLocationEditText.setText(it)
        })
    }

}
