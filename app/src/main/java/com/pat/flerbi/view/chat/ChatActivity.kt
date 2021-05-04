package com.pat.flerbi.view.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.pat.flerbi.R
import com.pat.flerbi.databinding.ActivityChatBinding
import com.pat.flerbi.viewholders.MsgToMeViewHolder
import com.pat.flerbi.viewholders.MsgToViewHolder
import com.pat.flerbi.viewmodel.ChatViewModel
import com.pat.flerbi.viewmodel.UserViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val chatViewModel by viewModel<ChatViewModel>()
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private var isReported = false
    private var isRecommended = false
    private var uid = FirebaseAuth.getInstance().uid
    private var roomKey = ""
    private var matchNick = ""
    private var matchUid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = null

        matchUid = intent.getStringExtra("OPP_UID").toString()
        matchNick = intent.getStringExtra("OPP_USERNAME").toString()
        roomKey = intent.getStringExtra("ROOM_KEY").toString()

        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChat.adapter = adapter
        binding.windowTitleTextView.text = matchNick

        observeMessages()
        observeReportError()
        observeRecommendError()
        chatViewModel.getMessages(roomKey, uid!!, matchUid)


        binding.sendMessageButton.setOnClickListener()
        {
            val msg = binding.typeMessageEditText.text.toString()
            chatViewModel.sendMessage(msg, roomKey, uid!!, matchUid)
            binding.typeMessageEditText.setText("")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.chat_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.reportUserItem -> {
               chatViewModel.reportUser(isReported, roomKey, matchUid)
                isReported = true
                true
            }
            R.id.recommendUserItem ->
            {
                chatViewModel.recommendUser(isRecommended, matchUid)
                isRecommended = true
                true
            }
            R.id.userTagsItem -> {
                //val bottomSheet = ChatTagsBottomSheet()
               // bottomSheet.show(supportFragmentManager, "Tag")
                true
            }
            R.id.end -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun observeMessages() {
        chatViewModel.chatMessages.observe(this, Observer {
            if (it.fromID == uid) adapter.add(MsgToViewHolder(it.msg))
            else adapter.add(MsgToMeViewHolder(it.msg))
        })
    }

    private fun observeReportError()
    {
     chatViewModel.reportUserError.observe(this, Observer {
         Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
     })
    }

    private fun observeRecommendError()
    {
        chatViewModel.recommendUserError.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }


}