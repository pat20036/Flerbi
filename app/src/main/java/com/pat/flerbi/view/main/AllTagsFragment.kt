package com.pat.flerbi.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentAllTagsBinding


class AllTagsFragment : Fragment() {

    private lateinit var binding: FragmentAllTagsBinding
    private val tagsArray = arrayListOf<String>()
    private val uid = FirebaseAuth.getInstance().uid
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
            tagsArray.clear()
            saveTags()

        }


    }


    private fun saveTags() {
        tagsArray.clear()
        val ids = binding.chipGroup.checkedChipIds
        for (id in ids) {
            val chip: Chip = binding.chipGroup.findViewById(id)
            tagsArray.add(chip.text.toString())
        }

        val ref = FirebaseDatabase.getInstance().getReference("user-tags/$uid/tags")
        ref.setValue(tagsArray).addOnCompleteListener {
            if (it.isSuccessful) {
                Snackbar.make(requireView(), "Pomyślnie zapisano", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

    }


}