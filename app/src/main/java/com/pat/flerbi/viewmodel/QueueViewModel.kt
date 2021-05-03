package com.pat.flerbi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.pat.flerbi.interfaces.QueueInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QueueViewModel(private val queueInterface: QueueInterface):ViewModel() {

    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean> get() = _isSearching

    private fun deleteQueueData()
    {
        CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            queueInterface.deleteQueueData()
        }
    }

    fun startSearch()
    {
     queueInterface.dataValidator().observeForever(Observer {
         _isSearching.value = it
     })
    }

    fun stopSearch()
    {
        queueInterface.stopSearch()
        deleteQueueData()
    }
}