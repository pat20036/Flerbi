package com.pat.flerbi.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentExternalLibsBinding

class ExternalLibsFragment : Fragment() {
    private lateinit var binding: FragmentExternalLibsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExternalLibsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.librariesBackButton.setOnClickListener()
        {
            findNavController().popBackStack()
        }
    }

}