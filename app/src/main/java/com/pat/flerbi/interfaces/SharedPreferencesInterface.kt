package com.pat.flerbi

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface SharedPreferencesInterface {
    fun getUserNickname(): String
    fun getActiveUsersCount(): String
}

class SharedPreferencesInterfaceImpl(context: Context) : SharedPreferencesInterface {
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


}