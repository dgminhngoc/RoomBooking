package com.example.roombooking.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.roombooking.R
import com.example.roombooking.controllers.SwitchFragmentController
import com.example.roombooking.utils.CommonUtils.hideSoftKeyboard

/**
 * Common base class of common implementation for a fragment will be used in this application
 */
abstract class BaseFragment : Fragment(), Animation.AnimationListener {
    var dataBundle: Bundle? = null

    /**
     * get status have allow start background task
     */
    /**
     * allow run background task
     */
    /**
     * The flag mark allow run background task when animation switch fragment end
     */
    var isAllowRunBackgroundTask = true

    /**
     * the content view of this fragment
     */
    private var contentView: ViewGroup? = null

    /**
     * the flag mark allow reload content view of this fragment when the [Fragment.onCreateView] method re-call
     */
    private val isReloadContent = false
    private var isOnTransition = false

    /**
     * replace a fragment and add to this fragment to back stack
     *
     * @param pChildFragment
     */
    protected fun <T : BaseFragment?> switchFragment(pChildFragment: T) {
        this.switchFragment<BaseFragment>(pChildFragment, null, true)
    }

    /**
     * replace a fragment and add to this fragment to back stack
     *
     * @param pChildFragment
     */
    protected fun <T : BaseFragment?> switchFragment(pChildFragment: T, dataBundle: Bundle?) {
        this.switchFragment<BaseFragment>(pChildFragment, dataBundle, true)
    }

    /**
     * replace a fragment and add to this fragment to back stack
     *
     * @param pBaseFragment
     */
    protected fun <T : BaseFragment?> switchFragment(pBaseFragment: BaseFragment?, dataBundle: Bundle?, isAddToBackStacks: Boolean) {
        this.switchFragment(R.id.containerView, fragmentManager, pBaseFragment, dataBundle, isAddToBackStacks)
    }

    /**
     * switch to a fragment,and add it to back stack
     *
     * @param pBaseChildFragment: the instance of fragment that want to show
     */
    protected fun <T : BaseFragment?> switchFragment(containerViewId: Int, pFragmentManager: FragmentManager?,
                                                     pBaseChildFragment: T, dataBundle: Bundle?, isAddToBackStack: Boolean) {
        SwitchFragmentController.switchFragment(containerViewId, pFragmentManager, pBaseChildFragment, dataBundle, isAddToBackStack)
    }

    protected fun switchToPreviousFragment(dataBundle: Bundle?) {
        SwitchFragmentController.switchToPreviousFragment(fragmentManager, dataBundle)
    }

    /**
     * clear all fragment has been added to back stack of a it's fragment manager
     */
    fun clearFragment() {
        SwitchFragmentController.clearFragment(fragmentManager)
    }

    /**
     * clear all fragment has been added to back stack of a it's fragment manager
     */
    fun clearFragment(fragmentManager: FragmentManager?) {
        SwitchFragmentController.clearFragment(fragmentManager)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var nextAnim = nextAnim
        super.onCreateAnimation(transit, enter, nextAnim)
        if (nextAnim == 0) {
            nextAnim = R.anim.fragment_slide_right_enter
        }
        val anim = AnimationUtils.loadAnimation(activity, nextAnim)
        if (anim != null && enter) {
            anim.setAnimationListener(this)
        }
        return anim
    }

    override fun onAnimationEnd(animation: Animation) {
        if (animation != null) {
            isOnTransition = false
            animation.cancel()
            animation.setAnimationListener(null)
        }
        /**
         * method show view when switch screen end.It's run in main thread
         */
        doShowViewWhenAnimationEnd()
        /**
         * call method manage run task in background when switch screen complete
         */
        doControlRunBackgroundTask()
    }

    override fun onAnimationStart(animation: Animation) {
        isOnTransition = true
    }

    override fun onAnimationRepeat(animation: Animation) {}

    /**
     * show view when animation end
     */
    protected fun doShowViewWhenAnimationEnd() {}

    /**
     * the method will be called when the animation switch screen end. Implements the background thread at here if
     * you want to start new background to do something when switch to this fragment
     */
    private fun doControlRunBackgroundTask() {
        if (isReloadContent) {
            isAllowRunBackgroundTask = true
        }
        if (isAllowRunBackgroundTask) {
            doInitialBackgroundThreadIfHave()
            isAllowRunBackgroundTask = false
        }
    }

    /**
     * method init background thread and start it if have
     */
    private fun doInitialBackgroundThreadIfHave() {}

    /**
     * pop to old fragment by tag
     *
     * @param tag : the tag of old fragment want to pop
     */
    protected fun popToFragmentByTag(tag: String?) {
        SwitchFragmentController.popToFragmentByTag(fragmentManager, tag)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("BaseFragment", "onCreateView")
        super.onCreateView(inflater, container, savedInstanceState)
        if (contentView == null || isReloadContent) {
            if (layoutContentID != LAYOUT_ID_BLANK) {
                contentView = inflater.inflate(layoutContentID, container, false) as ViewGroup
            }
        } else {
            val pViewParent = contentView!!.parent
            if (pViewParent != null) {
                (pViewParent as ViewGroup).removeView(contentView)
            }
        }
        return contentView
    }

    override fun onDestroyView() {
        destroyFragment()
        super.onDestroyView()
    }

    private fun destroyFragment() {
        hideSoftKeyboard(activity)
    }

    override fun onDestroy() {
        contentView = null
        super.onDestroy()
    }

    /**
     * event Back button is pressed
     */
    fun onBackPressed() {
        if (!isOnTransition) {
            SwitchFragmentController.switchToPreviousFragment(fragmentManager, null)
        }
    }

    /**
     * get content layout of this fragment
     *
     * @return layout id
     */
    protected abstract val layoutContentID: Int

    companion object {
        protected const val LAYOUT_ID_BLANK = -1
    }
}