package com.pat.flerbi.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentRegisterBinding
import com.pat.flerbi.model.RegisterError
import com.pat.flerbi.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var rePassword: String
    private lateinit var nickname: String
    private var touAccepted = false
    private val authViewModel by sharedViewModel<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        observeErrors()

        binding.registerButton.setOnClickListener()
        {
            clearDataErrors()
            getInputData()
            binding.registerProgressBar.visibility = View.VISIBLE
            authViewModel.registerUser(email, password, rePassword, nickname, touAccepted)
        }

        binding.registerBackButton.setOnClickListener()
        {
            findNavController().popBackStack()
        }


    }

    private fun clearDataErrors() {
        val emptyError = null
        binding.apply {
            passwordRegEditText.error = emptyError
            rePasswordRegEditText.error = emptyError
            emailRegEditText.error = emptyError
            nicknameRegEditText.error = emptyError
        }
    }

    private fun getInputData() {
        email = binding.emailRegEditText.editText?.text.toString()
        password = binding.passwordRegEditText.editText?.text.toString()
        rePassword = binding.rePasswordRegEditText.editText?.text.toString()
        nickname = binding.nicknameRegEditText.editText?.text.toString()
        val checkBox = binding.touCheckBox
        touAccepted = checkBox.isChecked
    }

    private fun observeErrors() {
        authViewModel.registerErrorMessages.observe(viewLifecycleOwner, Observer { errorList ->
            binding.registerProgressBar.visibility = View.INVISIBLE
            errorList.forEach {
                when (it) {
                    RegisterError.WRONG_PASSWORD -> {
                        binding.apply {
                            passwordRegEditText.error = it.description
                            rePasswordRegEditText.error = it.description
                        }
                    }
                    RegisterError.EMAIL_TAKEN -> binding.emailRegEditText.error = it.description

                    RegisterError.NICKNAME_TAKEN -> binding.nicknameRegEditText.error =
                        it.description

                    RegisterError.ACCEPT_TERMS -> Toast.makeText(
                        activity?.applicationContext,
                        it.description,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        )
    }
}