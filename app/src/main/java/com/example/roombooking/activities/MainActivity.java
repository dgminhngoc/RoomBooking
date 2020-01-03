package com.example.roombooking.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.roombooking.R;
import com.example.roombooking.fragments.QRCodeScannerFragment;
import com.example.roombooking.manager.SharedPreferencesManager;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_logouts:
				logout();
				break;
			default:
				break;
		}
		return true;
	}

	private void openQRCodeScanner()
	{
		switchFragment(new QRCodeScannerFragment());
	}

	private void logout()
	{
		SharedPreferencesManager.getInstance(this).clearData();

		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}
}
