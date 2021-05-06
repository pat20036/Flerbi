package com.pat.flerbi.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentSettingsBinding
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val userViewModel by sharedViewModel<UserViewModel>()
    private val bundle = Bundle()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.settingsEmailTextView.text = userViewModel.userEmail.value
        binding.settingsNicknameTextView.text = userViewModel.userNickname.value

        binding.settingsBackButton.setOnClickListener()
        {
            findNavController().popBackStack()
        }
        binding.settingsPasswordButton.setOnClickListener()
        {
            bundle.putInt("key", 1)
            findNavController().navigate(R.id.action_settingsFragment_to_confirmActionFragment, bundle)
        }
        binding.settingsDeleteAccountButton.setOnClickListener()
        {
            bundle.putInt("key", 2)
            findNavController().navigate(R.id.action_settingsFragment_to_confirmActionFragment, bundle)
        }
        binding.settingsContactButton.setOnClickListener()
        {
            findNavController().navigate(R.id.action_settingsFragment_to_contactFragment)
        }
        binding.settinsTermsButton.setOnClickListener()
        {
            findNavController().navigate(R.id.action_settingsFragment_to_termsFragment)
        }
        binding.settingsSuggestionsButton.setOnClickListener()
        {
            findNavController().navigate(R.id.action_settingsFragment_to_suggestionsFragment)
        }
        binding.settingsInfoButton.setOnClickListener()
        {
            findNavController().navigate(R.id.action_settingsFragment_to_appInfoFragment)
        }
        binding.settingsExternalButton.setOnClickListener()
        {
            findNavController().navigate(R.id.action_settingsFragment_to_externalLibsFragment)
        }
        binding.settingsImagesAuthorsButton.setOnClickListener()
        {
            findNavController().navigate(R.id.action_settingsFragment_to_imagesAuthorsFragment)
        }



    }


}