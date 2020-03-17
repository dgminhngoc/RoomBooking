package com.example.roombooking.fragments

import android.os.Bundle
import android.view.SurfaceHolder
import android.view.View
import com.example.roombooking.R
import com.example.roombooking.utils.CommonUtils.isQRCodeValid
import com.example.roombooking.utils.ConstKeyBundle
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.fragment_qrcode_scanner.*

class QRCodeScannerFragment : BaseFragment() {
    override val layoutContentID: Int = R.layout.fragment_qrcode_scanner

    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barcodeDetector = BarcodeDetector.Builder(activity)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build()
    }

    private fun init() {
        cameraSource = CameraSource.Builder(activity, barcodeDetector)
                .setAutoFocusEnabled(true)
                .build()
        surfaceView!!.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                cameraSource.start(surfaceView!!.holder)
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                //do nothings
            }

            override fun receiveDetections(detections: Detections<Barcode>) {
                val listBarcode = detections.detectedItems
                if (listBarcode.size() != 0) {
                    val qrString = listBarcode.valueAt(0).rawValue
                    textBarcodeValue.post {
                        if (isQRCodeValid(qrString)) {
                            bookRoom(qrString)
                        } else {
                            textBarcodeValue.text = activity!!.resources.getString(R.string.txt_no_valid_code)
                        }
                    }
                } else {
                    textBarcodeValue.post { textBarcodeValue.text = activity!!.resources.getString(R.string.txt_no_valid_code) }
                }
            }
        })
    }

    private fun bookRoom(roomName: String) {
        val dataBundle = Bundle()
        dataBundle.putString(ConstKeyBundle.KEY_ROOM_NAME, roomName)
        switchFragment(BookingFragment(), dataBundle)
    }

    override fun onPause() {
        super.onPause()
        cameraSource.release()
    }

    override fun onResume() {
        super.onResume()
        init()
    }
}