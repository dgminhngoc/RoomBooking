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
	private Bundle dataBundle = null;

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
	 * replace a fragment and add to this fragment to back stack
	 *
	 * @param pChildFragment
	 */
	protected <T extends BaseFragment> void switchFragment (T pChildFragment)
	{
		this.switchFragment(pChildFragment, null, true);
	}

	/**
	 * replace a fragment and add to this fragment to back stack
	 *
	 * @param pChildFragment
	 */
	protected <T extends BaseFragment> void switchFragment (T pChildFragment, Bundle dataBundle)
	{
		this.switchFragment(pChildFragment, dataBundle, true);
	}

	/**
	 * replace a fragment and add to this fragment to back stack
	 *
	 * @param pBaseFragment
	 */
	protected <T extends BaseFragment> void switchFragment (BaseFragment pBaseFragment, Bundle dataBundle, boolean isAddToBackStacks)
	{
		this.switchFragment(R.id.containerView, getFragmentManager(), pBaseFragment, dataBundle, isAddToBackStacks);
	}

	/**
	 * switch to a fragment,and add it to back stack
	 *
	 * @param pBaseChildFragment: the instance of fragment that want to show
	 */
	protected <T extends BaseFragment> void switchFragment (int containerViewId, FragmentManager pFragmentManager,
																			  T pBaseChildFragment, Bundle dataBundle, boolean isAddToBackStack)
	{
		SwitchFragmentController.switchFragment(containerViewId, pFragmentManager, pBaseChildFragment, dataBundle, isAddToBackStack);
	}

	protected void switchToPreviousFragment(Bundle dataBundle)
	{
		SwitchFragmentController.switchToPreviousFragment(getFragmentManager(), dataBundle);
	}

	/**
	 * clear all fragment has been added to back stack of a it's fragment manager
	 */
	public void clearFragment ()
	{
		SwitchFragmentController.clearFragment(getFragmentManager());
	}

	/**
	 * clear all fragment has been added to back stack of a it's fragment manager
	 */
	public void clearFragment (FragmentManager fragmentManager)
	{
		SwitchFragmentController.clearFragment(fragmentManager);
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
	 */
	public boolean isAllowRunBackgroundTask ()
	{
		return isAllowRunBackgroundTask;
	}

	/**
	 * allow run background task
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
		if (contentView == null || isReloadContent)
		{
			if (getLayoutContentID() != -1)
			{
				contentView = (ViewGroup) inflater.inflate(getLayoutContentID(), container, false);
			}
		}
		else
		{
			ViewParent pViewParent = contentView.getParent();
			if (pViewParent != null)
			{
				((ViewGroup) pViewParent).removeView(contentView);
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

	private void destroyFragment ()
	{
		CommonUtils.hideSoftKeyboard(getActivity());
	}

	@Override
	public void onDestroy ()
	{
		contentView = null;

		super.onDestroy();
	}

	public void setDataBundle(Bundle dataBundle)
	{
		this.dataBundle = dataBundle;
	}

	public Bundle getDataBundle()
	{
		return this.dataBundle;
	}

	/**
	 * event Back button is pressed
	 */
	public void onBackPressed()
	{
		if(!isOnTransition)
		{
			SwitchFragmentController.switchToPreviousFragment(getFragmentManager(), null);
		}
	}

	/**
	 * get content layout of this fragment
	 *
	 * @return layout id
	 */
	protected abstract int getLayoutContentID ();
}
