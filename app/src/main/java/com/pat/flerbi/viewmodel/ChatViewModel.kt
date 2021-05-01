package com.pat.flerbi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        val database = FirebaseDatabase.getInstance().getReference("/user-messages/$roomKey/$fromId/$toId")
        database.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val data = snapshot.getValue(UserMessage::class.java)
                data ?: return
                _chatMessages.value = data
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}