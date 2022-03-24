package com.example.firebasedatabaseexample.ui.userList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasedatabaseexample.R
import com.example.firebasedatabaseexample.domain.models.User

class UserAdapter : RecyclerView.Adapter<UserViewHolder>() {

    private var userData = mutableListOf<User>()

    var adapterClickListener: UserClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    fun update(newUsersData: List<User>) {
        with(userData) {
            clear()
            addAll(newUsersData)
        }

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(holder){
            bind(userData[position])
            setUserClickListener(adapterClickListener)
        }
    }

    override fun getItemCount() = userData.size

}