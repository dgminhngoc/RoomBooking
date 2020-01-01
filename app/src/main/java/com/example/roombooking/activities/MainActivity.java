package com.example.roombooking.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.roombooking.R;
import com.example.roombooking.fragments.QRCodeScannerFragment;

public class MainActivity extends BaseActivity
{
	private final int REQUEST_PERMISSION_CAMERA = 101;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		requestPermission();
	}

	private void requestPermission()
	{
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new
					String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
		}
		else
		{
			openQRCodeScanner();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		switch (requestCode)
		{
			case REQUEST_PERMISSION_CAMERA:
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					openQRCodeScanner();
				} else {
					this.finish();
				}
				break;
			default:
				break;
		}
	}

	private void openQRCodeScanner()
	{
		switchFragment(new QRCodeScannerFragment());
	}
}
