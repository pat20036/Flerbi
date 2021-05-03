package com.pat.flerbi.interfaces

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pat.flerbi.helpers.QueueInfo.location
import com.pat.flerbi.helpers.QueueInfo.roomNr
import com.pat.flerbi.helpers.QueueInfo.searchSecurity
import com.pat.flerbi.services.QueueService

interface QueueInterface {
    suspend fun deleteQueueData()
    fun dataValidator(): LiveData<Boolean>
    fun startSearch()
    fun stopSearch()

}

class QueueInterfaceImpl(private val context: Context) : QueueInterface {
    private val uid = FirebaseAuth.getInstance().uid!!
    private var isSearching: Boolean = false
    private val isSearchingLiveData = MutableLiveData<Boolean>()
    override suspend fun deleteQueueData() {
        val ref = FirebaseDatabase.getInstance().getReference("queue")
            .child("${location + roomNr}/$uid")
        ref.removeValue()
    }

    override fun dataValidator(): LiveData<Boolean> {
        if (location.length > 1 && location.isNotBlank() && searchSecurity == 0) {
            dataRefactor()
            startSearch()
            isSearching = true
            isSearchingLiveData.value = isSearching
        } else isSearching = false
        isSearchingLiveData.value = isSearching

        return isSearchingLiveData
    }

    private fun dataRefactor() {
        location = location.replace(" ", "")
    }

    override fun startSearch() {
        searchSecurity = 1
        roomNr = 1
        val intent = Intent(context.applicationContext, QueueService::class.java)
        context.startService(intent)
    }

    override fun stopSearch() {
        searchSecurity = 0
        isSearching = false
        isSearchingLiveData.value = isSearching
        val intent = Intent(context.applicationContext, QueueService::class.java)
        context.stopService(intent)
    }


}

