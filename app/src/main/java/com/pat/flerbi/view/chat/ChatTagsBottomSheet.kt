package com.pat.flerbi.view.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.pat.flerbi.R
import com.pat.flerbi.databinding.ChatTagsBottomSheetLayoutBinding
import com.pat.flerbi.helpers.QueueInfo.matchTags


class ChatTagsBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: ChatTagsBottomSheetLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatTagsBottomSheetLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in matchTags) {
            if (matchTags.isNotEmpty()) {
                val chip = Chip(binding.chatChipGroup.context)
                chip.text = i
                chip.setTextAppearance(R.style.CustomChipStyle)
                binding.chatChipGroup.addView(chip)
            }
        }

        if (matchTags.isEmpty()) {
            binding.tagsInfoTextView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.chatChipGroup.removeAllViews()
    }

}


