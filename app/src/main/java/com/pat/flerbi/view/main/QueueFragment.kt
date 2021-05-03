package com.pat.flerbi.view.main


import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        binding.searchButton.setOnClickListener()
        {
            location = binding.locationEditText.text.toString()
            queueViewModel.startSearch()
            userViewModel.setLastLocation(location)
        }
    }


    override fun onPause() {
        super.onPause()
        queueViewModel.stopSearch()
        queueViewModel.deleteQueueData(location, roomNr)
    }


}






