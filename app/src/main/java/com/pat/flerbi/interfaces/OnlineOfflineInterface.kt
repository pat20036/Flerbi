package com.pat.flerbi.interfaces

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

interface OnlineOfflineInterface {
    fun addToActiveUsers()
    fun removeFromActiveUsers()
}

class OnlineOfflineInterfaceImpl() : OnlineOfflineInterface {

    val uid = FirebaseAuth.getInstance().uid.toString()
    private val database = FirebaseDatabase.getInstance().getReference("active-users").child(uid)
    override fun addToActiveUsers() {
        database.setValue(uid)
    }

    override fun removeFromActiveUsers() {
        database.removeValue()
    }


}