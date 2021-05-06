package com.pat.flerbi.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentChangePasswordBinding
import com.pat.flerbi.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val authViewModel by viewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        observeResponse()
        binding.changePasswordButton.setOnClickListener()
        {
            val newPassword = binding.newPasswordEditText.editText?.text.toString()
            authViewModel.changePassword(newPassword)
        }
    }

    private fun observeResponse() {
        authViewModel.isPasswordChanged.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(activity?.applicationContext, "Changed!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            else binding.newPasswordEditText.error = "Error"
        })
    }

}