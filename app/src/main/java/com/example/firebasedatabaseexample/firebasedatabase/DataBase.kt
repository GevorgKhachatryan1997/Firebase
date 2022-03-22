package com.example.firebasedatabaseexample.firebasedatabase

import android.util.Log
import com.example.firebasedatabaseexample.user.User
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object DataBase {

    const val USERS = "users"
    private val database: DatabaseReference = Firebase.database.getReference(USERS)

    fun addUser(user: User): Task<Void> = database.push().setValue(user)

    fun updateUserData(kay: String, hashMap: HashMap<String, Any>): Task<Void> {
        return database.child(kay).updateChildren(hashMap)
    }

    fun getUsers() {
        FirebaseDatabase.getInstance().reference.child(USERS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null) {
                            user.name?.let { Log.d(this::class.java.simpleName, it) }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }
}

