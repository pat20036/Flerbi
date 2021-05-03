package com.pat.flerbi.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.pat.flerbi.databinding.ActivityChatBinding
import com.pat.flerbi.viewholders.MsgToMeViewHolder
import com.pat.flerbi.viewholders.MsgToViewHolder
import com.pat.flerbi.viewmodel.ChatViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
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

        observeMessages()
        chatViewModel.getMessages(roomKey, uid!!, matchUid)


        binding.sendMessageButton.setOnClickListener()
        {
            val msg = binding.typeMessageEditText.text.toString()
            chatViewModel.sendMessage(msg, roomKey, uid!!, matchUid)
            binding.typeMessageEditText.setText("")
        }
    }

    private fun observeMessages() {
        chatViewModel.chatMessages.observe(this, Observer {
            if (it.fromID == uid) adapter.add(MsgToViewHolder(it.msg, "Nick"))
            else adapter.add(MsgToMeViewHolder(it.msg, "Nick"))
        })
    }
}