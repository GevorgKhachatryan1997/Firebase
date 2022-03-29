package com.example.firebasedatabaseexample.data.firebasedatabase

import android.util.Log
import com.example.firebasedatabaseexample.domain.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserDataBase {

    var userDataChangeListener: ((List<User>) -> Unit)? = null

    private val database: DatabaseReference = Firebase.database.getReference(USERS).apply {
        addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userDataChangeListener?.invoke(getUsersList(snapshot))
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun addUser(user: User): Task<Void> = database.push().setValue(user)

    fun updateUserData(kay: User,name: String,lastName: String): Task<Void> {

        val map = mapOf(KAY_USER_NAME to name, KAY_USER_LASTNAME to lastName)

        

        return database.child(kay.userId.toString()).updateChildren(map).addOnSuccessListener {
            Log.d(UserDataBase::class.java.simpleName,"update success")
        }
    }

    fun getUsers(resultCallback: (List<User>) -> Unit) {
        FirebaseDatabase.getInstance().reference.child(USERS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    resultCallback(getUsersList(dataSnapshot))
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }

    fun removeUserWithUserId(userId: String?) {
        FirebaseDatabase
            .getInstance()
            .reference
            .child(USERS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val user = snapshot.getValue(User::class.java)

                        if (user?.userId == userId) {
                            val userDataBaseReference = FirebaseDatabase
                                .getInstance()
                                .reference
                                .child(USERS)
                                .child(snapshot.key.toString())

                            userDataBaseReference.removeValue()
                            break
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }

    private fun getUsersList(dataSnapshot: DataSnapshot): MutableList<User> {
        val userList = mutableListOf<User>()
        for (snapshot in dataSnapshot.children) {
            val user = snapshot.getValue(User::class.java)
            if (user != null) {
                userList.add(user)
            }
        }
        return userList
    }

    companion object{
        private const val USERS = "users"
        private const val KAY_USER_NAME = "name"
        private const val KAY_USER_LASTNAME = "lastname"
    }
}

