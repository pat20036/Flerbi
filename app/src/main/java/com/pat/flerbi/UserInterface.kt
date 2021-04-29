package com.pat.flerbi

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface UserInterface {
fun getUserNickname():String
}

class UserInterfaceImpl(context: Context):UserInterface
{
    private val sharedPreferences =
        context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)

    override fun getUserNickname():String = sharedPreferences.getString("nick", "?")!!

}