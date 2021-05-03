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
import com.pat.flerbi.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel by sharedViewModel<AuthViewModel>()
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
        authViewModel.loginErrorMessages.observe(viewLifecycleOwner, Observer { errors ->
            errors.forEach {
                binding.loginProgressBar.visibility = View.INVISIBLE
                if (it.id == 0) {
                    binding.emailLoginEditText.error = it.description
                }
                if (it.id == 1) {
                    binding.passwordLoginEditText.error = it.description
                }

                if (it.id == 2) {
                    binding.passwordLoginEditText.error = it.description
                }

                if (it.id == 3) {
                    binding.passwordLoginEditText.error = it.description
                }

                if (it.id == 4) {
                    binding.emailLoginEditText.error = it.description
                }

                if (it.id == 5) {
                    binding.emailLoginEditText.error = it.description
                }

            }
        })
    }

}