package com.pat.flerbi.interfaces

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pat.flerbi.view.main.QueueFragment

interface QueueInterface {
    fun deleteQueueData(location:String, roomNr:Int)
}

class QueueInterfaceImpl():QueueInterface
{
    private val uid = FirebaseAuth.getInstance().uid!!
    override fun deleteQueueData(location: String, roomNr: Int) {
        val ref = FirebaseDatabase.getInstance().getReference("queue")
            .child("${QueueFragment.location + QueueFragment.roomNr}/$uid")
        ref.removeValue()
    }

}