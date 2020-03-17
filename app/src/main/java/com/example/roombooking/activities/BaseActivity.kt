package com.example.roombooking.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.roombooking.R
import com.example.roombooking.controllers.SwitchFragmentController.getCurrentFragment
import com.example.roombooking.controllers.SwitchFragmentController.switchFragment
import com.example.roombooking.fragments.BaseFragment

abstract class BaseActivity : AppCompatActivity() {
    /**
     * switch to a fragment, and add it to back stack
     *
     * @param pBaseChildFragment: the instance of fragment that want to show
     */
    protected fun <T : BaseFragment?> switchFragment(pBaseChildFragment: T) {
        switchFragment(R.id.containerView, supportFragmentManager, pBaseChildFragment, null, true)
    }

    /**
     * switch to a fragment, and add it to back stack
     *
     * @param pBaseChildFragment: the instance of fragment that want to show
     */
    protected fun <T : BaseFragment?> switchFragment(pBaseChildFragment: T, dataBundle: Bundle?) {
        switchFragment(R.id.containerView, supportFragmentManager, pBaseChildFragment, dataBundle, true)
    }

    /**
     * switch to a fragment, and add it to back stack if defined
     *
     * @param pBaseChildFragment: the instance of fragment that want to show
     * @param isAddToBackStack: add pBaseChildFragment to back stack if needed
     */
    protected fun <T : BaseFragment?> switchFragment(pBaseChildFragment: T, dataBundle: Bundle?, isAddToBackStack: Boolean) {
        switchFragment(R.id.containerView, supportFragmentManager, pBaseChildFragment, dataBundle, isAddToBackStack)
    }

    override fun onBackPressed() {
        val mFragment = getCurrentFragment(supportFragmentManager)
        mFragment?.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) finish()
    }
}