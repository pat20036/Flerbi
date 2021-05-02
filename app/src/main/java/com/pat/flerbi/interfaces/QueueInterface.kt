package com.pat.flerbi.interfaces

import android.content.Context
import android.content.Intent
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pat.flerbi.QueueInfo
import com.pat.flerbi.QueueInfo.location
import com.pat.flerbi.QueueInfo.roomNr
import com.pat.flerbi.QueueInfo.searchSecurity
import com.pat.flerbi.QueueService
import com.pat.flerbi.R
import com.pat.flerbi.view.main.QueueFragment

interface QueueInterface {
   suspend fun deleteQueueData()
   fun dataValidator()
   fun startSearch()
   fun stopSearch()
}

class QueueInterfaceImpl(private val context: Context):QueueInterface
{
    private val uid = FirebaseAuth.getInstance().uid!!
    override suspend fun deleteQueueData() {
        val ref = FirebaseDatabase.getInstance().getReference("queue")
            .child("${location + roomNr}/$uid")
        ref.removeValue()
    }

    override fun dataValidator() {
        if (location.length > 1 && location.isNotBlank() && searchSecurity == 0) {
            data()
            startSearch()
        }else{
            //error
        }

        }
    private fun data() {
        QueueInfo.location.replace(" ", "")
    }

    override fun startSearch()
    {
        searchSecurity = 1
        roomNr = 1
        val intent = Intent(context.applicationContext, QueueService::class.java)
        context.startService(intent)
    }

    override fun stopSearch() {
        searchSecurity = 0
        val intent = Intent(context.applicationContext, QueueService::class.java)
        context.stopService(intent)
    }

}
