package com.example.firebasedatabaseexample.ui.userList

import com.example.firebasedatabaseexample.domain.models.User

fun interface UserClickListener {
    fun userClickListener(user: User)
}