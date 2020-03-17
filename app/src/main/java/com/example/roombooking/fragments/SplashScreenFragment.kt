package com.example.roombooking.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.roombooking.R
import com.example.roombooking.activities.MainActivity
import com.example.roombooking.manager.SharedPreferencesManager

class SplashScreenFragment : BaseFragment() {
    override val layoutContentID: Int = R.layout.fragment_splash_screen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val runnable = Runnable {
           activity?.let {
               if (SharedPreferencesManager.getInstance(it)!!.userToken != null) {
                   startActivity(Intent(activity, MainActivity::class.java))
                   activity!!.finish()
               } else {
                   switchFragment<BaseFragment>(LoginScreenFragment(), null, false)
               }
           }
        }
        val checkUserTokenHandler = Handler()
        checkUserTokenHandler.postDelayed(runnable, 2000)
    }
}