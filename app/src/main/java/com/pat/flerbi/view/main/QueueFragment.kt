package com.pat.flerbi.view.main


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pat.flerbi.QueueInfo.location
import com.pat.flerbi.QueueInfo.roomNr
import com.pat.flerbi.QueueInfo.searchSecurity
import com.pat.flerbi.QueueService
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentQueueBinding
import com.pat.flerbi.viewmodel.QueueViewModel
import com.pat.flerbi.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class QueueFragment : Fragment() {

    private lateinit var binding: FragmentQueueBinding
    private val queueViewModel by sharedViewModel<QueueViewModel>()
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
        }
    }


    override fun onPause() {
        super.onPause()
        queueViewModel.stopSearch()
        queueViewModel.deleteQueueData(location, roomNr)
    }


}






