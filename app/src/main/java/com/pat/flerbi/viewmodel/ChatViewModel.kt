package com.pat.flerbi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.pat.flerbi.interfaces.DatabaseChatRepositoryInterface
import com.pat.flerbi.model.UserMessage

class ChatViewModel(private val databaseChatRepositoryInterface: DatabaseChatRepositoryInterface) :
    ViewModel() {

    private val _chatMessages = MutableLiveData<UserMessage>()
    val chatMessages: LiveData<UserMessage> get() = _chatMessages

    private val _reportUserError = MutableLiveData<String>()
    val reportUserError: LiveData<String> get() = _reportUserError

    private val _recommendUserError = MutableLiveData<String>()
    val recommendUserError: LiveData<String> get() = _recommendUserError

    fun sendMessage(msg: String, roomKey: String, fromId: String, toId: String) {
        databaseChatRepositoryInterface.sendMessage(msg, roomKey, fromId, toId)
    }

    fun getMessages(roomKey: String, fromId: String, toId: String) {
        databaseChatRepositoryInterface.getMessages(roomKey, fromId, toId).observeForever(Observer {
            _chatMessages.value = it
        })
    }

    fun reportUser(isReported: Boolean, roomKey: String, matchUid: String) {
        databaseChatRepositoryInterface.reportUser(isReported, roomKey, matchUid)
            .observeForever(Observer {
                _reportUserError.value = it
            })
    }

    fun recommendUser(isRecommended: Boolean, matchUid: String) {
        databaseChatRepositoryInterface.recommendUser(isRecommended, matchUid)
            .observeForever(Observer {
                _recommendUserError.value = it
            })
    }

}