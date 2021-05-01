package com.pat.flerbi.interfaces

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pat.flerbi.model.UserMessage

interface DatabaseChatRepositoryInterface {
    fun sendMessage(msg: String, roomKey: String, fromId: String, toId: String)

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
}
