package com.example.firebasedatabaseexample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.firebasedatabaseexample.R
import com.example.firebasedatabaseexample.data.firebasedatabase.UserDataBase
import com.example.firebasedatabaseexample.domain.models.User
import com.example.firebasedatabaseexample.ui.MainActivity.Companion.USER_BUNDLE_KAY

class UserDialogFragment : DialogFragment(R.layout.user_dialog_fragment) {

    private var etUserNewDataName: EditText? = null
    private var etUserNewDataLastname: EditText? = null
    private var btnUpdateNewUserData: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.user_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        btnUpdateNewUserData?.setOnClickListener {
            updateUserData()
            Log.d(UserDialogFragment::class.java.simpleName, "user data updated")
        }
    }

    private fun updateUserData() {
        val userDataBase = UserDataBase()
        val user = arguments?.getParcelable<User>(USER_BUNDLE_KAY)
        if (user != null) {
            userDataBase.updateUserData(user, etUserNewDataName?.text.toString(), etUserNewDataLastname?.text.toString())
        }
    }

    private fun init() {
        etUserNewDataName = requireView().findViewById(R.id.etUserDataName)
        etUserNewDataLastname = requireView().findViewById(R.id.etUserDataLastName)
        btnUpdateNewUserData = requireView().findViewById(R.id.btnUpdateUser)
    }
}

