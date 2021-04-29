package com.pat.flerbi.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentLoginBinding
import com.pat.flerbi.viewmodel.AuthViewModel
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
        binding.forgotPasswordTextView.setOnClickListener()
        {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.loginButton.setOnClickListener()
        {
            getInputData()
            authViewModel.loginUser(email, password)
        }


    }

    private fun getInputData() {
        email = binding.emailLoginEditText.editText?.text.toString()
        password = binding.passwordLoginEditText.editText?.text.toString()
    }

}