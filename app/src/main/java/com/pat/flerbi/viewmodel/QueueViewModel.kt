package com.pat.flerbi.viewmodel

import androidx.lifecycle.ViewModel
import com.pat.flerbi.interfaces.QueueInterface

class QueueViewModel(private val queueInterface: QueueInterface):ViewModel() {

    fun deleteQueueData(location: String, roomNr: Int)
    {
        queueInterface.deleteQueueData(location, roomNr)
    }
}