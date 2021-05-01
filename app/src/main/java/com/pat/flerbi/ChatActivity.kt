package com.pat.flerbi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.pat.flerbi.databinding.ActivityChatBinding
import com.pat.flerbi.viewholders.MsgToMeViewHolder
import com.pat.flerbi.viewholders.MsgToViewHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val chatViewModel by viewModel<ChatViewModel>()
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private var reportSecure = 0
    private var recommendSecure = 0
    private var uid = FirebaseAuth.getInstance().uid
    private var points = 0
    private var roomKey = ""
    private var matchNick = ""
    private var matchUid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        matchUid = intent.getStringExtra("OPP_UID").toString()
        matchNick = intent.getStringExtra("OPP_USERNAME").toString()
        roomKey = intent.getStringExtra("ROOM_KEY").toString()

        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChat.adapter = adapter

        chatViewModel.getMessages(roomKey, uid!!, matchUid)

        chatViewModel.chatMessages.observe(this, Observer {
            if(it.fromID == uid) adapter.add(MsgToViewHolder(it.msg, "Nick"))
            else adapter.add(MsgToMeViewHolder(it.msg, "Nick"))
        })
        binding.sendMessageButton.setOnClickListener()
        {
            val msg = binding.typeMessageEditText.text.toString()
            chatViewModel.sendMessage(msg, roomKey, uid!!, matchUid)
        }

    }
}