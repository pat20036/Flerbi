package com.pat.flerbi.interfaces

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
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
    fun setDarkTheme(switchState: Boolean): Boolean
    fun checkIsDarkTheme():Boolean
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

    override fun setDarkTheme(switchState: Boolean): Boolean {
        if(switchState)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPreferences.edit().putBoolean("dark_theme", true).apply()
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPreferences.edit().putBoolean("dark_theme", false).apply()
        }

        val darkTheme = sharedPreferences.getBoolean("dark_theme", false)

        if (darkTheme)  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    return darkTheme
    }

    override fun checkIsDarkTheme(): Boolean = sharedPreferences.getBoolean("dark_theme", false)



}