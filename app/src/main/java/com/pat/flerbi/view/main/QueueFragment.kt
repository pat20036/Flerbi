package com.pat.flerbi.view.main


import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.pat.flerbi.helpers.QueueInfo.location
import com.pat.flerbi.helpers.QueueInfo.roomNr
import com.pat.flerbi.databinding.FragmentQueueBinding
import com.pat.flerbi.viewmodel.QueueViewModel
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class QueueFragment : Fragment() {
    private lateinit var binding: FragmentQueueBinding
    private val queueViewModel by sharedViewModel<QueueViewModel>()
    private val userViewModel by sharedViewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQueueBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        observeIsSearching()
        observeFavoriteLocation()
        binding.searchButton.setOnClickListener()
        {
            location = binding.locationEditText.text.toString()
            userViewModel.setLastLocation(location)
            queueViewModel.startSearch()
        }
        binding.cancelButton.setOnClickListener()
        {
            queueViewModel.stopSearch()
        }
    }

    override fun onPause() {
        super.onPause()
        queueViewModel.stopSearch()
    }

    private fun observeIsSearching() {
        queueViewModel.isSearching.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.searchLayout.visibility = View.VISIBLE
                binding.searchButton.visibility = View.GONE
            } else {
                binding.searchLayout.visibility = View.GONE
                binding.searchButton.visibility = View.VISIBLE
            }
        })
    }

    private fun observeFavoriteLocation()
    {
        userViewModel.userFavoriteLocation.observe(viewLifecycleOwner, Observer {
            binding.locationEditText.setText(it)
        })
    }


}






