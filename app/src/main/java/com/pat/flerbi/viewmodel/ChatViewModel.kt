package com.pat.flerbi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.pat.flerbi.interfaces.DatabaseChatRepositoryInterface
import com.pat.flerbi.model.UserMessage

class ChatViewModel(private val databaseChatRepositoryInterface: DatabaseChatRepositoryInterface):ViewModel() {

    private val _chatMessages = MutableLiveData<UserMessage>()
    val chatMessages: LiveData<UserMessage> get() = _chatMessages

    private val _reportUserError = MutableLiveData<String>()
    val reportUserError: LiveData<String> get() = _reportUserError

    fun sendMessage(msg:String, roomKey:String, fromId:String, toId:String)
    {
        databaseChatRepositoryInterface.sendMessage(msg, roomKey, fromId, toId)
    }

    fun getMessages(roomKey: String, fromId: String, toId: String)
    {
        databaseChatRepositoryInterface.getMessages(roomKey, fromId, toId).observeForever(Observer {
            _chatMessages.value = it
        })
    }

    fun reportUser(reportSecure: Int, roomKey: String, matchUid: String)
    {
        databaseChatRepositoryInterface.reportUser(reportSecure, roomKey, matchUid).observeForever( Observer {
                _reportUserError.value = it
            })
    }

}