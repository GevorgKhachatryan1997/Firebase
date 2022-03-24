package com.example.firebasedatabaseexample.ui.userList

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasedatabaseexample.R
import com.example.firebasedatabaseexample.domain.models.User

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var user: User? = null
    private var tvUser: TextView = itemView.findViewById(R.id.user)
    private var btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    private var userClickListener: UserClickListener? = null

    init {
        btnDelete.setOnClickListener {
            user?.let { userClickListener?.userClickListener(it) }
        }
    }

    fun bind(user: User) {
        this.user = user
        tvUser.text = user.name
    }

    fun setUserClickListener(clickListener: UserClickListener?) {
        userClickListener = clickListener
    }
}