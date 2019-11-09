package com.example.roombooking.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.roombooking.R;
import com.example.roombooking.controllers.SwitchFragmentController;
import com.example.roombooking.utils.CommonUtils;

/**
 * Common base class of common implementation for a fragment will be used in this application
**/
public abstract class BaseFragment extends Fragment implements Animation.AnimationListener
{
	/**
	 * The flag mark allow run background task when animation switch fragment end
	 */
	private boolean isAllowRunBackgroundTask = true;
	/**
	 * the content view of this fragment
	 */
	private ViewGroup contentView;
	/**
	 * the flag mark allow reload content view of this fragment when the {@link Fragment#onCreateView} method re-call
	 */
	private boolean isReloadContent = false;

	private boolean isOnTransition = false;

	/**
	 * switch to a fragment,and add it to backstack
	 *
	 * @param pBaseChildFragment: the instance of fragment that want to show
	 */
	protected <T extends BaseFragment> void switchFragment (int containerViewId, FragmentManager pFragmentManager,
																			  T pBaseChildFragment, boolean isAddToBackStack)
	{
		SwitchFragmentController.switchFragment(containerViewId, pFragmentManager, pBaseChildFragment, isAddToBackStack);
	}

	/**
	 * replace a fragment and add to this fragment to back stack
	 *
	 * @param pChildFragment
	 */
	protected <T extends BaseFragment> void switchFragment (T pChildFragment)
	{
		this.switchFragment(pChildFragment, true);
	}

	/**
	 * replace a fragment and add to this fragment to back stack
	 *
	 * @param pBaseFragment
	 */
	protected <T extends BaseFragment> void switchFragment (BaseFragment pBaseFragment, boolean isAddToBackStacks)
	{
		this.switchFragment(R.id.containerView, getFragmentManager(), pBaseFragment, isAddToBackStacks);
	}

	/**
	 * clear all fragment has been added to back stack of a it's fragemnt manager
	 */
	public void clearFragment ()
	{
		SwitchFragmentController.clearFragment(getFragmentManager());
	}

	@Override
	public Animation onCreateAnimation (int transit, boolean enter, int nextAnim)
	{
		super.onCreateAnimation(transit, enter, nextAnim);
		if (nextAnim == 0)
		{
			nextAnim = R.anim.fragment_slide_right_enter;
		}
		Animation anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);
		if (anim != null && enter)
		{
			anim.setAnimationListener(this);
		}
		return anim;
	}

	@Override
	public final void onAnimationEnd (Animation animation)
	{
		if (animation != null)
		{
			isOnTransition = false;
			animation.cancel();
			animation.setAnimationListener(null);
		}
		/**
		 * method show view when switch screen end.It's run in main thread
		 */
		this.doShowViewWhenAnimationEnd();
		/**
		 * call method manage run task in background when switch screen complete
		 */
		this.doControlRunBackgroundTask();
	}

	@Override
	public final void onAnimationStart (Animation animation)
	{
		isOnTransition = true;
	}

	@Override
	public final void onAnimationRepeat (Animation animation)
	{

	}

	/**
	 * show view when animation end
	 */
	protected void doShowViewWhenAnimationEnd ()
	{

	}

	/**
	 * the method will be called when the animation switch screen end. Implements the background thread at here if
	 * you want to start new background to do something when switch to this fragment
	 */
	protected void doControlRunBackgroundTask ()
	{
		if (isReloadContent)
		{
			isAllowRunBackgroundTask = true;
		}
		if (isAllowRunBackgroundTask)
		{
			this.doInitialBackgroundThreadIfHave();
			isAllowRunBackgroundTask = false;
		}
	}

	/**
	 * get status have allow start background task
	 *
	 * @return
	 */
	public boolean isAllowRunBackgroundTask ()
	{
		return isAllowRunBackgroundTask;
	}

	/**
	 * allow run background task
	 *
	 * @param isAllowRunBackgroundTask
	 */
	public void setAllowRunBackgroundTask (boolean isAllowRunBackgroundTask)
	{
		this.isAllowRunBackgroundTask = isAllowRunBackgroundTask;
	}

	/**
	 * method init background thread and start it if have
	 */
	protected void doInitialBackgroundThreadIfHave ()
	{
	}

	/**
	 * pop to old fragment by tag
	 *
	 * @param tag : the tag of old fragment want to pop
	 */
	protected void popToFragmentByTag (String tag)
	{
		SwitchFragmentController.popToFragmentByTag(getFragmentManager(), tag);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.d("BaseFragment", "onCreateView");
		super.onCreateView(inflater, container, savedInstanceState);
		if (contentView == null || isReloadContent == true)
		{
			if (getLayoutContentID() != -1)
			{
				contentView = (ViewGroup) inflater.inflate(getLayoutContentID(), container, false);
			}
		}
		else
		{
			if (contentView != null)
			{
				ViewParent pViewParent = contentView.getParent();
				if (pViewParent != null)
				{
					((ViewGroup) pViewParent).removeView(contentView);
				}
			}
		}

		return contentView;
	}

	@Override
	public void onDestroyView ()
	{
		this.destroyFragment();
		super.onDestroyView();
	}

	protected void destroyFragment ()
	{
		CommonUtils.hideSoftKeyboard(getActivity());
	}

	@Override
	public void onDestroy ()
	{
		contentView = null;

		super.onDestroy();
	}

	/**
	 * event Back button is pressed
	 */
	public void onBackPressed()
	{
		if(!isOnTransition)
		{
			SwitchFragmentController.switchToPreviousFragment(getFragmentManager());
		}
	}

	/**
	 * get content layout of this fragment
	 *
	 * @return layout id
	 */
	protected abstract int getLayoutContentID ();
}
