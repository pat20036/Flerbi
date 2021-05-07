package com.pat.flerbi.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentTagsBinding
import com.pat.flerbi.model.Tag
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TagsFragment : Fragment() {
    private lateinit var binding: FragmentTagsBinding
    private val userViewModel by sharedViewModel<UserViewModel>()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTagsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        observeUserTags()

        binding.editTagsButton.setOnClickListener()
        {
            findNavController().navigate(R.id.action_tagsFragment_to_allTagsFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.myTagsChipGroup.removeAllViews()
        userViewModel.getProfileTags()
    }

    private fun observeUserTags() {
        userViewModel.profileTags.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty())
                showInfo()
            else showTags(it)
        })
    }

    private fun showTags(myTags: List<Tag>) {
        val myTagsChipGroup = binding.myTagsChipGroup
        binding.loadingTagsProgressBar.visibility = View.GONE
        for (i in myTags) {
            val chip = Chip(myTagsChipGroup.context)
            chip.text = i.name
            chip.setTextAppearance(R.style.CustomChipStyle)
            myTagsChipGroup.addView(chip)
        }
    }

    private fun showInfo() {
        binding.loadingTagsProgressBar.visibility = View.GONE
        binding.tagsInfoTextView.visibility = View.VISIBLE
        val assignButton = binding.assignTagsButton
        assignButton.visibility = View.VISIBLE
        assignButton.setOnClickListener()
        {
            findNavController().navigate(R.id.action_tagsFragment_to_allTagsFragment)
        }
    }

}