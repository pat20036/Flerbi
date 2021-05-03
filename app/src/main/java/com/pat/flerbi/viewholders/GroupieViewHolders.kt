package com.pat.flerbi.viewholders

import android.widget.TextView
import com.pat.flerbi.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class MsgToViewHolder(val text: String) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.msg_to).text = text

    }

    override fun getLayout(): Int {
        return R.layout.msg_to

    }
}

class MsgToMeViewHolder(val text: String) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.msg_to_me).text = text

    }

    override fun getLayout(): Int {
        return R.layout.msg_to_me
    }
}