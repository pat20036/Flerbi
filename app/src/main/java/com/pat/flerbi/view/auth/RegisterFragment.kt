package com.pat.flerbi.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputLayout
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentRegisterBinding
import com.pat.flerbi.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var rePassword: String
    private lateinit var reNickname: String
    private var touAccepted = false
    private val authViewModel by viewModel<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.registerButton.setOnClickListener()
        {
            clearDataErrors()
            getInputData()
            authViewModel.registerUser(email, password, rePassword, reNickname, touAccepted)
        }
    }

    private fun clearDataErrors() {
        binding.passwordRegEditText.error = null
        binding.rePasswordRegEditText.error = null
        binding.emailRegEditText.error = null
        binding.nicknameRegEditText.error = null
        val checkBox = binding.touCheckBox
        touAccepted = checkBox.isChecked


    }

    private fun getInputData() {
        email =
            view?.findViewById<TextInputLayout>(R.id.emailRegEditText)?.editText?.text.toString()
        password =
            view?.findViewById<TextInputLayout>(R.id.passwordRegEditText)?.editText?.text.toString()
        rePassword =
            view?.findViewById<TextInputLayout>(R.id.rePasswordRegEditText)?.editText?.text.toString()
        reNickname =
            view?.findViewById<TextInputLayout>(R.id.nicknameRegEditText)?.editText?.text.toString()
    }
}