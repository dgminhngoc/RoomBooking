package com.example.roombooking.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.roombooking.R
import com.example.roombooking.manager.ServerDummy.Companion.instance
import com.example.roombooking.manager.SharedPreferencesManager.Companion.getInstance
import com.example.roombooking.utils.CommonUtils.currentDateTime
import com.example.roombooking.utils.CommonUtils.isNetworkAvailable
import com.example.roombooking.utils.ConstKeyBundle
import com.example.roombooking.utils.ConstRequestResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_room_booking.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class BookingFragment : BaseFragment() {

    override val layoutContentID: Int = R.layout.fragment_room_booking

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataBundle = dataBundle
        if (dataBundle != null) {
            val roomName = dataBundle.getString(ConstKeyBundle.KEY_ROOM_NAME)
            edtRoomName.setText(roomName)
            val btnCancel = view.findViewById<Button>(R.id.btnClear)
            btnCancel.setOnClickListener(btnCancelOnClickListener())
            val btnBook = view.findViewById<Button>(R.id.btnBook)
            btnBook.setOnClickListener(btnBookOnClickListener())
            val currentDateTime = currentDateTime.split(" ").toTypedArray()
            val time = time
            edtDate.setText(currentDateTime[0])
            edtStartTime.setText(time[0] +":"+ time[1])
            Log.i("MyApp", edtStartTime.text.toString())
        }
    }

    private val time: Array<String>
         get() {
            val bookingTime = SimpleDateFormat("HHmm").format(Calendar.getInstance().time)
            val hour = bookingTime.substring(0, 2)
            val minute = bookingTime.substring(2, 4)
            var intHour = hour.toInt()
            var intMinute = minute.toInt()
            if (intMinute >= 30) {
                if (intMinute % 30 > BOOKING_LIMIT) {
                    intHour += 1
                    intMinute = 0
                } else {
                    intMinute = 30
                }
            } else if (intMinute < 30) {
                intMinute = if (intMinute % 30 > BOOKING_LIMIT) {
                    30
                } else {
                    0
                }
            }
            val strHour = intHour.toString()
            val strMinute: String
            strMinute = if (intMinute < 10) {
                "0$intMinute"
            } else {
                Integer.toString(intMinute)
            }
            return arrayOf(strHour, strMinute)
        }

    private fun btnCancelOnClickListener(): View.OnClickListener {
        return View.OnClickListener { onBackPressed() }
    }

    private fun btnBookOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            activity?.let {
                if (!isNetworkAvailable(it)) {
                    Toast.makeText(activity, activity!!.resources.getString(R.string.txt_err_no_connection), Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                val userToken = getInstance(it)?.userToken
                val date = edtDate!!.text.toString()
                val startTime = edtStartTime!!.text.toString()
                val duration = edtDuration!!.text.toString()
                if (userToken != null) {
                    val formatter = SimpleDateFormat("dd/MM/yyyy")
                    try {
                        val mdate = formatter.parse(date)
                        val mStartTime = startTime.split(":").toTypedArray()
                        val dateInMillis = mdate.time + (mStartTime[0].toInt() * 60 + mStartTime[1].toInt() * 60 * 1000)
                        val finalTime = (mStartTime[0] + mStartTime[1]).toInt()
                        sendBookingRequestToServer(userToken, finalTime, duration.toInt(), edtRoomName.text.toString())
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun sendBookingRequestToServer(userToken: String, startTimeInMillis: Int, duration: Int, roomName: String) {
        val roomBookingTask = RoomBookingTask(this, userToken, startTimeInMillis, duration, roomName)
        roomBookingTask.execute()
    }

    private fun informBookingSucceed() {
        MaterialAlertDialogBuilder(activity)
                .setTitle(activity!!.resources.getString(R.string.dialog_txt_room_reserve_success_title))
                .setMessage(activity!!.resources.getString(R.string.txt_room_reserve_success))
                .setPositiveButton(activity!!.resources.getString(R.string.btn_txt_book_other_room)) { dialogInterface, i -> onBackPressed() }
                .setNegativeButton(activity!!.resources.getString(R.string.btn_txt_exit)) { dialogInterface, i -> activity!!.finish() }
                .show()
    }

     class RoomBookingTask(private val bookingFragment: BookingFragment?, private val userToken: String, private val startTimeInMillis: Int, private val duration: Int, private val roomName: String) : AsyncTask<Void?, Void?, Int>() {

        override fun doInBackground(vararg voids: Void?): Int {
            return instance!!.checkRoomAvailability(userToken, startTimeInMillis, duration, roomName)
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            if (bookingFragment != null) {
                when (result) {
                    ConstRequestResult.RE_AVAILABLE
                        -> bookingFragment.informBookingSucceed()
                    ConstRequestResult.RE_ERR_ROOM_NOT_AVAILABLE
                        -> Toast.makeText(bookingFragment.activity, bookingFragment.activity!!.resources.getString(R.string.txt_romm_reserve_not_available), Toast.LENGTH_SHORT).show()
                    ConstRequestResult.RE_ERR_DURATION_TOO_LONG
                        -> Toast.makeText(bookingFragment.activity, bookingFragment.activity!!.resources.getString(R.string.txt_romm_reserve_duration_too_long), Toast.LENGTH_SHORT).show()
                    else -> {
                    }
                }
            }
        }

    }

    companion object {
        private const val BOOKING_LIMIT = 5
    }
}