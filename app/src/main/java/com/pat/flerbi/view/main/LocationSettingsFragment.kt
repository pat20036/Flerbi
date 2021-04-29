package com.pat.flerbi.main

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.pat.flerbi.R


class LocationSettingsFragment : Fragment() {


    override fun onStart() {
        super.onStart()
        readFavoriteLocation()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       val view =  inflater.inflate(R.layout.fragment_location_settings, container, false)

        val fav_location_bt = view?.findViewById<Button>(R.id.favorite_button)

        fav_location_bt?.setOnClickListener()
        {
            val favLocation = view.findViewById<EditText>(R.id.favorite_location_edittext)?.text.toString()
            val favLocationCb = view.findViewById<CheckBox>(R.id.auto_fav_location_checkbox)
            var favLocationCbStatus = false

            if(favLocationCb.isChecked)
            {
             favLocationCbStatus= true
            }


            if(favLocation.length in 1..2)
            {
                Toast.makeText(activity?.applicationContext, "Błąd", Toast.LENGTH_SHORT).show()
            }

            else
            {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
                val sharedPreferencesEditor = sharedPreferences.edit()
                sharedPreferencesEditor.putString("fav_location", favLocation)
                sharedPreferencesEditor.putBoolean("fav_location_cb", favLocationCbStatus)
                sharedPreferencesEditor.apply()
                readFavoriteLocation()
                Toast.makeText(activity?.applicationContext, "Zapisano", Toast.LENGTH_SHORT).show()

            }

        }

        return view
    }

    private fun readFavoriteLocation()
    {
        val favLocationCb = view?.findViewById<CheckBox>(R.id.auto_fav_location_checkbox)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
        val favLocation  = sharedPreferences.getString("fav_location", "")
        val favLocationCbStatus = sharedPreferences.getBoolean("fav_location_cb", false)

        if(favLocationCbStatus == true)
        {
         favLocationCb?.isChecked = true
        }
        view?.findViewById<TextView>(R.id.favorite_location_edittext)?.text = "$favLocation"


    }



    }
