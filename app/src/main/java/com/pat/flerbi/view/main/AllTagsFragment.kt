package com.pat.flerbi.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.pat.flerbi.databinding.FragmentAllTagsBinding
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AllTagsFragment : Fragment() {

    private lateinit var binding: FragmentAllTagsBinding
    private val userViewModel by sharedViewModel<UserViewModel>()
    private val tagsList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllTagsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.tagsBackButton.setOnClickListener()
        {
            findNavController().popBackStack()
        }
       binding.saveTagsButton.setOnClickListener()
        {
            tagsList.clear()
            observeMessage()
            saveTags()
        }
    }

    private fun saveTags() {
        tagsList.clear()
        val ids = binding.chipGroup.checkedChipIds
        for (id in ids) {
            val chip: Chip = binding.chipGroup.findViewById(id)
            tagsList.add(chip.text.toString())
        }
        userViewModel.saveUserTags(tagsList)

    }

    private fun observeMessage()
    {
     userViewModel.allTagsInfo.observe(viewLifecycleOwner, Observer {
         Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
         findNavController().popBackStack()
     })

    }

}