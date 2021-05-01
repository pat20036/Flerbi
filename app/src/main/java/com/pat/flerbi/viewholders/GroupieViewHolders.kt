package com.pat.flerbi.viewholders

import android.widget.TextView
import com.pat.flerbi.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class MsgToViewHolder(val text: String, val nick: String) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.findViewById<TextView>(R.id.msg_to).text = text
        //val text=  viewHolder.itemView.findViewById<TextView>(R.id.username_msg_to)
        //  text.text = nick
    }

    override fun getLayout(): Int {
        return R.layout.msg_to

    }
}

class MsgToMeViewHolder(val text: String, val nick: String) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.msg_to_me).text = text
        //viewHolder.itemView.findViewById<TextView>(R.id.username_msg_to_me).text = nick
    }

    override fun getLayout(): Int {
        return R.layout.msg_to_me
    }
}