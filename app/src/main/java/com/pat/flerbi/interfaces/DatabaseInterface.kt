package com.pat.flerbi

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface DatabaseInterface {
    fun addToActiveUsers()
    fun removeFromActiveUsers()
}

class DatabaseInterfaceImpl() : DatabaseInterface {
    private val uid = FirebaseAuth.getInstance().uid!!
    private val database = FirebaseDatabase.getInstance().getReference("active-users").child(uid)
    override fun addToActiveUsers() {
        database.setValue(uid)
    }

    override fun removeFromActiveUsers() {
        database.removeValue()
    }


}