package com.pat.flerbi.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentLoginBinding
import com.pat.flerbi.model.LoginError
import com.pat.flerbi.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel by viewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        observeErrors()

        binding.forgotPasswordTextView.setOnClickListener()
        {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.loginButton.setOnClickListener()
        {
            clearDataError()
            getInputData()
            binding.loginProgressBar.visibility = View.VISIBLE
            authViewModel.loginUser(email, password)
        }

        binding.logInBackButton.setOnClickListener()
        {
            findNavController().popBackStack()
        }
    }

    private fun getInputData() {
        email = binding.emailLoginEditText.editText?.text.toString()
        password = binding.passwordLoginEditText.editText?.text.toString()
    }

    private fun clearDataError() {
        binding.emailLoginEditText.error = null
        binding.passwordLoginEditText.error = null
    }

    private fun observeErrors() {
        authViewModel.loginErrorMessages.observe(viewLifecycleOwner, Observer { errorList ->
            errorList.forEach {
                binding.loginProgressBar.visibility = View.INVISIBLE
                when(it)
                {
                    LoginError.PASSWORD_LENGTH -> binding.passwordLoginEditText.error = it.description
                    LoginError.EMPTY_PASSWORD -> binding.passwordLoginEditText.error = it.description
                    LoginError.EMPTY_EMAIL -> binding.emailLoginEditText.error = it.description
                    LoginError.INCORRECT_EMAIL -> binding.emailLoginEditText.error = it.description
                    LoginError.INCORRECT_PASSWORD -> binding.passwordLoginEditText.error = it.description
                    LoginError.USER_NOT_FOUND -> binding.emailLoginEditText.error = it.description
                }
            }
        })
    }

}