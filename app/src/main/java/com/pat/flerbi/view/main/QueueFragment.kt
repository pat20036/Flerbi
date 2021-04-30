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
import com.pat.flerbi.QueueService
import com.pat.flerbi.R
import com.pat.flerbi.databinding.FragmentQueueBinding


class QueueFragment : Fragment() {
    companion object {
        const val TAG = "TAG"
        var location = ""
        var nick = "Username"
        var roomNr = 1
        var searchSecurity = 0

    }

    private var uid = FirebaseAuth.getInstance().uid
    private lateinit var binding: FragmentQueueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQueueBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        //val animation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.fade_in)
        binding.searchButton.setOnClickListener()
        {
            val textLoc = binding.locationEditText.text
            val textLocLength = binding.locationEditText.text.length
            if (textLocLength > 1 && textLoc.isNotBlank() && searchSecurity == 0) {
                searchSecurity = 1
                roomNr = 1
                startSearch()
            } else {
                Snackbar.make(requireView(), "Niepoprawne dane!", Snackbar.LENGTH_SHORT).show()
            }
        }
       // animation.duration = 1500
       // searchButton.startAnimation(animation)
    }

    override fun onResume() {
        super.onResume()
        //readFavoriteLocation()

    }

    override fun onPause() {
        super.onPause()
        activity?.stopService(Intent(context, QueueService::class.java))
        val handler = Handler()
        handler.postDelayed(Runnable {
            searchSecurity = 0
            val ref = FirebaseDatabase.getInstance().getReference("queue")
                .child("${location + roomNr}/$uid")
            ref.removeValue()
            view?.findViewById<ConstraintLayout>(R.id.search_bar)?.visibility = View.GONE
        }, 500)


    }


//    private fun saveLastLocation() {
//        val sharedPreferences =
//            PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
//        var lastLocation = view?.findViewById<EditText>(R.id.location_text)?.text.toString()
//
//        val sharedPreferencesEditor = sharedPreferences.edit()
//        sharedPreferencesEditor.putString("last_location", lastLocation)
//        sharedPreferencesEditor.apply()
//
//        val lastLocationSP = sharedPreferences.getString(PREF_LOCATION, PREF_DEFAULT).toString()
//
//        view?.findViewById<TextView>(R.id.lastLocationTextView)?.text = "$lastLocationSP"
//
//    }
//
//
//    private fun readFavoriteLocation() {
//        val sharedPreferences =
//            PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
//        val favLocation = sharedPreferences.getString("fav_location", "Brak")
//        val favLocationCbStatus = sharedPreferences.getBoolean("fav_location_cb", false)
//
//        if (favLocationCbStatus)  view?.findViewById<EditText>(R.id.location_text)?.setText("$favLocation")
//        if (!favLocationCbStatus) view?.findViewById<EditText>(R.id.location_text)?.requestFocus()
//
//
//
//    }
//
//
    private fun startSearch() {
       // val animation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.fade_in)
        val searchBar = view?.findViewById<ConstraintLayout>(R.id.search_bar)
        val handler = Handler()
        handler.postDelayed(Runnable { // Do something after 5s = 5000ms
            searchBar?.visibility = View.VISIBLE
            //searchBar?.animation = animation
            view?.findViewById<ConstraintLayout>(R.id.tipsLayout)?.visibility = View.GONE
            data()
            activity?.startService(Intent(context, QueueService::class.java))
        }, 1)


        view?.findViewById<TextView>(R.id.cancel)?.setOnClickListener()
        {
            searchSecurity = 0
            activity?.stopService(Intent(context, QueueService::class.java))
            val ref = FirebaseDatabase.getInstance().getReference("queue")
                .child("${location + roomNr}")
            ref.removeValue()
            view?.findViewById<ConstraintLayout>(R.id.tipsLayout)?.visibility = View.VISIBLE
            searchBar?.visibility = View.GONE
        }
        //saveLastLocation()


    }


    private fun data() {
        val locationNoEdit = view?.findViewById<EditText>(R.id.locationEditText)?.text.toString()
        //locationTextView.text = locationNoEdit
        location = locationNoEdit.replace(" ", "")


    }

}






