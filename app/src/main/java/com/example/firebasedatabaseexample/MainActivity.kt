package com.example.firebasedatabaseexample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasedatabaseexample.firebasedatabase.DataBase
import com.example.firebasedatabaseexample.user.User

class MainActivity : AppCompatActivity() {

    var btnAddUser: Button? = null
    var btnUpdate: Button? = null
    var btnRemove: Button? = null
    var etName: EditText? = null
    var etLastName: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        btnAddUser?.setOnClickListener {
            val name = etName?.text.toString()
            val lastName = etLastName?.text.toString()
            if (name.isNullOrBlank() || lastName.isNullOrBlank()) return@setOnClickListener

            val user = User( name, lastName)
            DataBase.addUser(user)
                .addOnSuccessListener {
                Toast.makeText(this, "user added", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(this, "error - ${it.message}", Toast.LENGTH_SHORT).show()
            }.addOnCompleteListener {
                Toast.makeText(this, "user added", Toast.LENGTH_SHORT).show()
            }.addOnCanceledListener {
                Toast.makeText(this, "user added", Toast.LENGTH_SHORT).show()
            }
        }

        btnUpdate?.setOnClickListener {
            val hashMap: HashMap<String,Any> = HashMap()
            hashMap.put("name",etName?.text.toString())
            hashMap.put("lastname",etLastName?.text.toString())

            DataBase.updateUserData("-Myhnle1spqvXMOqHmoN",hashMap).addOnSuccessListener {
                Toast.makeText(this, "user data update", Toast.LENGTH_SHORT).show()
            }

        }

        btnRemove?.setOnClickListener {

        }

        DataBase.getUsers()

    }

    private fun init(){
        btnAddUser = findViewById(R.id.btnAddUser)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnRemove = findViewById(R.id.btnRemove)
        etName = findViewById(R.id.etName)
        etLastName = findViewById(R.id.etLastName)
    }
}
