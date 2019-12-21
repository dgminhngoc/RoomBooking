package com.example.roombooking.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roombooking.R;
import com.example.roombooking.activities.BaseActivity;
import com.example.roombooking.activities.HomeScreen;
import com.example.roombooking.activities.LoginActivity;
import com.example.roombooking.activities.MainActivity;
import com.example.roombooking.activities.ScannerActivity;
import com.example.roombooking.camera.GraphicOverlay;

public class BarcodeScannerFragment extends BaseFragment implements GraphicOverlay.IGraphicOverlayOnDrawFinishedListener
{
	@Override
	protected int getLayoutContentID()
	{
		return R.layout.fragment_barcode_scanner;
	}

	@Override
	public void onDrawFinished()
	{

	}
}
