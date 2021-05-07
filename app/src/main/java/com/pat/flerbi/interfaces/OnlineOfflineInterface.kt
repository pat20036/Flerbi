package com.pat.flerbi.interfaces

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

interface OnlineOfflineInterface {
    fun addToActiveUsers(uid: String)
    fun removeFromActiveUsers(uid: String)
}

class OnlineOfflineInterfaceImpl() : OnlineOfflineInterface {

    private val databaseReference = FirebaseDatabase.getInstance().getReference("active-users")

    override fun addToActiveUsers(uid: String) {
        databaseReference.child(uid).setValue(uid)
    }

    override fun removeFromActiveUsers(uid: String) {
        databaseReference.child(uid).removeValue()
    }


}