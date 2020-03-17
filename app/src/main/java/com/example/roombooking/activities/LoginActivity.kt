package com.example.roombooking.activities

import android.os.Bundle
import com.example.roombooking.R
import com.example.roombooking.fragments.SplashScreenFragment

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        switchFragment(SplashScreenFragment())
    }
}