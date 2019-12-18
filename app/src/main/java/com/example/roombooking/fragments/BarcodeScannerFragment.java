package com.example.roombooking.fragments;

import com.example.roombooking.R;
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
