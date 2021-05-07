package com.pat.flerbi.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.pat.flerbi.databinding.FragmentForgotPasswordBinding
import com.pat.flerbi.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private val authViewModel by sharedViewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        observeResponse()
        binding.sendMailButton.setOnClickListener()
        {
            val email = binding.emailForgetEditText.editText?.text.toString()
            authViewModel.remindPassword(email)
        }
    }

    private fun observeResponse()
    {
        authViewModel.remindResponse.observe(viewLifecycleOwner, Observer {
            if(it) binding.mailMessageTextView.visibility = View.VISIBLE
            else binding.emailForgetEditText.error = "Error!"
        })
    }

}