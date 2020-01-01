package com.example.roombooking.fragments;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roombooking.R;
import com.example.roombooking.utils.CommonUtils;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRCodeScannerFragment extends BaseFragment
{
	private SurfaceView surfaceView;
	private BarcodeDetector barcodeDetector;
	private CameraSource cameraSource;

	private TextView txtBarcodeValue;


	@Override
	protected int getLayoutContentID()
	{
		return R.layout.fragment_barcode_scanner;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		surfaceView = view.findViewById(R.id.surfaceView);
		txtBarcodeValue = view.findViewById(R.id.txtBarcodeValue);

		barcodeDetector = new BarcodeDetector.Builder(getActivity())
				.setBarcodeFormats(Barcode.QR_CODE	)
				.build();
	}

	private void init()
	{
		cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector)
				.setAutoFocusEnabled(true)
				.build();

		surfaceView.getHolder().addCallback(new SurfaceHolder.Callback()
		{
			@Override
			public void surfaceCreated(SurfaceHolder holder)
			{
				try
				{
					cameraSource.start(surfaceView.getHolder());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
			{
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder)
			{
				cameraSource.stop();
			}
		});

		barcodeDetector.setProcessor(new Detector.Processor<Barcode>()
		{
			@Override
			public void release()
			{
//				Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void receiveDetections(Detector.Detections<Barcode> detections)
			{
				final SparseArray<Barcode> barcodes = detections.getDetectedItems();
				Log.i("MyApp", "size = " + barcodes.size());
				if (barcodes.size() != 0)
				{
					final String qrString = barcodes.valueAt(0).rawValue;
					txtBarcodeValue.post(new Runnable()
					{
						@Override
						public void run()
						{
							if(CommonUtils.isQRCodeValid(qrString))
								txtBarcodeValue.setText(barcodes.valueAt(0).rawValue);
							else
								txtBarcodeValue.setText(getActivity().getResources().getString(R.string.txt_no_valid_code));
						}
					});
				}
			}
		});
	}

	@Override
	public void onPause()
	{
		super.onPause();
		cameraSource.release();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		init();
	}
}
