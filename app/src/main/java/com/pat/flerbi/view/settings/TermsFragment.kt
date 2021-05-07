package com.pat.flerbi.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentTermsBinding


class TermsFragment : Fragment() {
    private lateinit var binding: FragmentTermsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.termsBackButton.setOnClickListener()
        {
            findNavController().popBackStack()
        }
    }

}