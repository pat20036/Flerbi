package com.pat.flerbi

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pat.flerbi.view.auth.WelcomeActivity

interface UserInterface {
fun getUserEmail():String
fun logoutUser()
}

class UserInterfaceImpl(private val context: Context):UserInterface
{
    override fun getUserEmail():String = FirebaseAuth.getInstance().currentUser.email

    override fun logoutUser() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context.applicationContext , WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)

    }

}