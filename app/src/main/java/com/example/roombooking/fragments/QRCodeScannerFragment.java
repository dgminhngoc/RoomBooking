package com.example.roombooking.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roombooking.utils.CommonUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeScannerFragment extends BaseFragment implements ZXingScannerView.ResultHandler
{
	private ZXingScannerView scannerView;

	@Override
	protected int getLayoutContentID()
	{
		return 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		List<BarcodeFormat> listBarcodeFormat = new ArrayList<>();
		listBarcodeFormat.add(BarcodeFormat.QR_CODE);
		scannerView = new ZXingScannerView(getActivity());
		scannerView.setFormats(listBarcodeFormat);

		return scannerView;
	}

	@Override
	public void onResume() {
		super.onResume();
		scannerView.setResultHandler(this);
		scannerView.startCamera();
	}

	@Override
	public void onPause() {
		super.onPause();
		scannerView.stopCamera();
	}

	@Override
	public void handleResult(Result result) {
		final String qrString = result.getText();
		if(CommonUtils.isQRCodeValid(qrString))
		{
			//return back result to previous fragment
		}
		else
		{
			//qrcode should be scanned again
		}
	}
}
