package com.pat.flerbi.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.pat.flerbi.model.Report
import com.pat.flerbi.model.UserMessage

interface DatabaseChatRepositoryInterface {
    fun sendMessage(msg: String, roomKey: String, fromId: String, toId: String)
    fun getMessages(roomKey: String, fromId: String, toId: String): LiveData<UserMessage>
    fun reportUser(reportSecure: Int, roomKey: String, matchUid: String):LiveData<String>

}

class DatabaseChatRepositoryInterfaceImpl : DatabaseChatRepositoryInterface {
    override fun sendMessage(msg: String, roomKey: String, fromId: String, toId: String) {

        if (msg.isNotBlank()) {
            val reference =
                FirebaseDatabase.getInstance().getReference("/user-messages/$roomKey/$fromId/$toId")
                    .push()
            val toReference =
                FirebaseDatabase.getInstance().getReference("/user-messages/$roomKey/$toId/$fromId")
                    .push()

            val chatMessage = UserMessage(reference.key!!, msg, fromId, toId)
            reference.setValue(chatMessage)
            toReference.setValue(chatMessage)
        }
    }

    override fun getMessages(roomKey: String, fromId: String, toId: String): LiveData<UserMessage> {
        val chatMessage = MutableLiveData<UserMessage>()
        val database = FirebaseDatabase.getInstance().getReference("/user-messages/$roomKey/$fromId/$toId")
        database.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val data = snapshot.getValue(UserMessage::class.java)
                data ?: return
                chatMessage.value = data
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
        return chatMessage
    }

    override fun reportUser(reportSecure:Int ,roomKey: String, matchUid: String): LiveData<String> {
        val infoLiveData = MutableLiveData<String>()
        val uid = FirebaseAuth.getInstance().currentUser?.uid!!
        if(reportSecure == 0)
        {
            val ref = FirebaseDatabase.getInstance().getReference("reported-users/$roomKey").push()
            ref.setValue(Report(matchUid, uid)).addOnCompleteListener {
                infoLiveData.value = "Reported"
            }
        }
        else infoLiveData.value = "User has already been reported"

        return infoLiveData
    }
}
