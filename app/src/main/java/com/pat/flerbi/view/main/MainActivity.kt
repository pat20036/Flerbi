package com.pat.flerbi.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pat.flerbi.R
import com.pat.flerbi.databinding.ActivityMainBinding
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModel<UserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_main)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnNavigationItemReselectedListener {
            return@setOnNavigationItemReselectedListener
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                /*R.id.darkThemeFragment,*/R.id.locationSettingsFragment, R.id.allTagsFragment -> bottomNavigationView.visibility =
                    View.GONE
                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }
        userViewModel.addToActiveUsers()
    }

    override fun onDestroy() {
        super.onDestroy()
        userViewModel.removeFromActiveUsers()
    }
}