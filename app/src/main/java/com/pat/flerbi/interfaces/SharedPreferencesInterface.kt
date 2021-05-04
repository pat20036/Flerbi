package com.pat.flerbi.interfaces

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pat.flerbi.R

interface SharedPreferencesInterface {
    fun getUserNickname(): String
    fun getActiveUsersCount(): String
    fun setFavoriteLocation(location: String)
    fun getFavoriteLocation():String
    fun setLastLocation(location:String)
    fun getLastLocation():String
}

class SharedPreferencesInterfaceImpl(private val context: Context) : SharedPreferencesInterface {
    private val sharedPreferences =
        context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)

    override fun getUserNickname(): String = sharedPreferences.getString("nick", "?")!!

    override fun getActiveUsersCount(): String {
        val database = FirebaseDatabase.getInstance().getReference("active-users")
        database.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               sharedPreferences.edit().putString("active_users", snapshot.childrenCount.toString()).apply()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return sharedPreferences.getString("active_users", "0")!!
    }

    override fun setFavoriteLocation(location:String) {
        sharedPreferences.edit().putString("favorite_location", location).apply()
    }

    override fun getFavoriteLocation(): String = sharedPreferences.getString("favorite_location", "")!!

    override fun setLastLocation(location:String) {
        sharedPreferences.edit().putString("last_location", location).apply()
    }

    override fun getLastLocation(): String =  sharedPreferences.getString("last_location", context.getString(R.string.empty))!!




}