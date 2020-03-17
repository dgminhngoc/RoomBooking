package com.example.roombooking.fragments

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.roombooking.R
import com.example.roombooking.activities.MainActivity
import com.example.roombooking.manager.ServerDummy
import com.example.roombooking.manager.SharedPreferencesManager.Companion.getInstance
import com.example.roombooking.utils.CommonUtils.isEMailValid
import kotlinx.android.synthetic.main.fragment_login_screen.*

class LoginScreenFragment : BaseFragment() {

    override val layoutContentID: Int = R.layout.fragment_login_screen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            val userName = edtUserName.text.toString()
            val password = edtPassword.text.toString()
            if (isEMailValid(userName)) {
                login(userName, password)
            } else {
                Toast.makeText(activity, activity!!.resources.getString(R.string.txt_err_login_wrong_account), Toast.LENGTH_SHORT).show()
            }
        }

        btnClear.setOnClickListener {
            edtUserName.text?.clear()
            edtPassword.text?.clear()
        }
    }

    private fun login(userName: String, password: String) {
        val requestLoginTask = RequestLoginTask(this, userName, password)
        requestLoginTask.execute()
    }
}

class RequestLoginTask(private var fragment: Fragment?, private val userName: String, private val password: String) : AsyncTask<Void?, Void?, String?>() {

    override fun doInBackground(vararg voids: Void?): String? {
        val server = ServerDummy.instance
        return if (server!!.checkValidUser(userName, password)) ServerDummy.USER_TOKEN else null
    }

    override fun onPostExecute(loginToken: String?) {
        super.onPostExecute(loginToken)
        if (fragment != null && fragment!!.activity != null) {
            if (loginToken != null) {
                getInstance(fragment!!.activity!!)!!.userToken = loginToken
                fragment!!.startActivity(Intent(fragment!!.activity, MainActivity::class.java))
                fragment!!.activity!!.finish()
            } else {
                Toast.makeText(fragment!!.activity, fragment!!.activity!!.resources.getString(R.string.txt_err_login_wrong_account), Toast.LENGTH_SHORT).show()
            }
            fragment = null
        }
    }
}