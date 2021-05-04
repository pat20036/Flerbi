package com.pat.flerbi.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentRegisterBinding
import com.pat.flerbi.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var rePassword: String
    private lateinit var reNickname: String
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
            authViewModel.registerUser(email, password, rePassword, reNickname, touAccepted)
        }
    }

    private fun clearDataErrors() {
        binding.passwordRegEditText.error = null
        binding.rePasswordRegEditText.error = null
        binding.emailRegEditText.error = null
        binding.nicknameRegEditText.error = null
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

        val checkBox = binding.touCheckBox
        touAccepted = checkBox.isChecked
    }

    private fun observeErrors() {
        authViewModel.registerErrorMessages.observe(viewLifecycleOwner, Observer { error ->
            error.forEach {
                binding.registerProgressBar.visibility = View.INVISIBLE
                if (it.id == 0) {
                    binding.passwordRegEditText.error = it.description
                    binding.rePasswordRegEditText.error = it.description
                }
                if (it.id == 1) {
                    Toast.makeText(activity?.applicationContext, it.description, Toast.LENGTH_SHORT)
                        .show()
                }

                if (it.id == 2) {
                    binding.nicknameRegEditText.error = it.description
                }

                if (it.id == 3) {
                    binding.emailRegEditText.error = it.description
                }
            }

        })

    }

}