package com.pat.flerbi.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentDeleteAccountBinding
import com.pat.flerbi.viewmodel.AuthViewModel
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DeleteAccountFragment : Fragment() {
    private lateinit var binding:FragmentDeleteAccountBinding
    private val authViewModel by viewModel<AuthViewModel>()
    private val userViewModel by sharedViewModel<UserViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val nickname = userViewModel.userNickname.value!!
        observeResponse()
        binding.confirmDeleteAccountButton.setOnClickListener()
        {
            authViewModel.deleteAccount(nickname)
        }
    }
    private fun observeResponse()
    {
        authViewModel.isAccountDeleted.observe(viewLifecycleOwner, Observer {
            if(!it) Toast.makeText(activity?.applicationContext, "Error", Toast.LENGTH_SHORT).show()
        })
    }
}