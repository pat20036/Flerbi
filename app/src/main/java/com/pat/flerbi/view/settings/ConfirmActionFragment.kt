package com.pat.flerbi.view.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentConfirmActionBinding
import com.pat.flerbi.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ConfirmActionFragment : Fragment() {
    private lateinit var binding: FragmentConfirmActionBinding
    private val authViewModel by viewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmActionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val email = FirebaseAuth.getInstance().currentUser.email!!
        observeResponse()
        binding.confirmPasswordButton.setOnClickListener()
        {
            clearErrors()
            binding.confirmIdentityProgressBar.visibility = View.VISIBLE
            val password = binding.confirmPasswordEditText.editText?.text.toString()
            authViewModel.confirmIdentity(email, password)
        }
    }

    private fun observeResponse()
    {
        val code = arguments?.getInt("key")
        authViewModel.isIdentityConfirmed.observe(viewLifecycleOwner, Observer {
            Log.d("www", it.toString())
            if(it && code == 1) findNavController().navigate(R.id.action_confirmActionFragment_to_changePasswordFragment)
            if(it && code == 2) findNavController().navigate(R.id.action_confirmActionFragment_to_deleteAccountFragment)
            else
            {
                binding.confirmIdentityProgressBar.visibility = View.INVISIBLE
                binding.confirmPasswordEditText.error = "Failure"
            }
        })
    }

    private fun clearErrors()
    {
        binding.confirmPasswordEditText.error = null
    }

}