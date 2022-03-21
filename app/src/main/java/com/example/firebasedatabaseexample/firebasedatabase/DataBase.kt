package com.example.firebasedatabaseexample.firebasedatabase

import com.example.firebasedatabaseexample.user.User
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.collections.HashMap

object DataBase {

    const val USERS = "users"
    private val database: DatabaseReference = Firebase.database.getReference(USERS)

    fun addUser(user: User): Task<Void> = database.push().setValue(user)

    fun updateUserData(kay: String, hashMap: HashMap<String, Any>): Task<Void> {
        return database.child(kay).updateChildren(hashMap)
    }

    fun removeUser() {

    }
}

