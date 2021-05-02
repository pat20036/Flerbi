package com.pat.flerbi.viewmodel

import androidx.lifecycle.ViewModel
import com.pat.flerbi.interfaces.QueueInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QueueViewModel(private val queueInterface: QueueInterface):ViewModel() {

    fun deleteQueueData(location: String, roomNr: Int)
    {
        CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            queueInterface.deleteQueueData()
        }
    }

    fun startSearch()
    {
        queueInterface.dataValidator()
    }

    fun stopSearch()
    {
        queueInterface.stopSearch()
    }
}