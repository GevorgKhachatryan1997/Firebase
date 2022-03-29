package com.example.firebasedatabaseexample.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasedatabaseexample.R
import com.example.firebasedatabaseexample.data.firebasedatabase.UserDataBase
import com.example.firebasedatabaseexample.domain.models.User
import com.example.firebasedatabaseexample.ui.userList.UserAdapter
import com.example.firebasedatabaseexample.ui.userList.UserClickListener
import java.util.*

class MainActivity : AppCompatActivity() {

    private var btnUpdate: Button? = null
    private var btnRemove: Button? = null
    private var etName: EditText? = null
    private var etLastName: EditText? = null
    private var btnAddUser: Button? = null
    private var userRecycleView: RecyclerView? = null
    private val userDataBase = UserDataBase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        initRecycleView()

        btnAddUser?.setOnClickListener {
            saveUser()
        }

        btnUpdate?.setOnClickListener {
            userDataBase.getUsers { usersList ->
                usersList.forEach { user ->
                    userDataBase.updateUserData(
                        user,
                        etName?.text.toString(),
                        etLastName?.text.toString()
                    ).addOnSuccessListener {
                        Toast.makeText(this, "user data update", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun saveUser() {
        val name = etName?.text.toString()
        val lastName = etLastName?.text.toString()
        val userId = UUID.randomUUID().toString()

        if (name.isBlank() || lastName.isBlank() || userId.isBlank()) {
            Toast.makeText(this, "data cant be empty", Toast.LENGTH_SHORT).show()
        } else {
            val user = User(name, lastName, userId)

            userDataBase.addUser(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "user added", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "error - ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun initRecycleView() {
        userRecycleView = findViewById<
                RecyclerView>(R.id.userRecyclerView).apply {
            val userAdapter = UserAdapter()
            adapter = userAdapter

            userDataBase.getUsers { listUsers ->
                userAdapter.update(listUsers)
            }

            userAdapter.adapterClickListener = UserClickListener { user ->
                userDataBase.removeUserWithUserId(user.userId)
            }

            userDataBase.userDataChangeListener = {
                userAdapter.update(it)
            }

            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun init() {
        btnAddUser = findViewById(R.id.btnAddUser)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnRemove = findViewById(R.id.btnRemove)
        etName = findViewById(R.id.etName)
        etLastName = findViewById(R.id.etLastName)
    }
}
