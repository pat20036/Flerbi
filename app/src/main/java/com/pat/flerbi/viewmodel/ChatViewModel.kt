package com.pat.flerbi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.pat.flerbi.interfaces.DatabaseChatRepositoryInterface
import com.pat.flerbi.model.UserMessage

class ChatViewModel(private val databaseChatRepositoryInterface: DatabaseChatRepositoryInterface):ViewModel() {

    private val _chatMessages = MutableLiveData<UserMessage>()
    val chatMessages: LiveData<UserMessage> get() = _chatMessages

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

}