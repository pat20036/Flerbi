package com.pat.flerbi.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.pat.flerbi.view.main.MainActivity
import com.pat.flerbi.databinding.ActivityWelcomeBinding
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeActivity : AppCompatActivity() {
    ///////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////CREATED BY: @pat20036 github.com/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    private lateinit var binding: ActivityWelcomeBinding
    private val userViewModel by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel.checkIsDarkTheme()
        observeDarkTheme()
        if(FirebaseAuth.getInstance().currentUser != null)
        {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun observeDarkTheme()
    {
       val darkTheme = userViewModel.darkTheme.value!!
        if(darkTheme)  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

}