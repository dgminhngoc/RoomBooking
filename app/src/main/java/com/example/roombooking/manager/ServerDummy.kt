package com.example.roombooking.manager

import com.example.roombooking.utils.CommonUtils
import com.example.roombooking.utils.ConstRequestResult
import java.util.*

class ServerDummy private constructor() {
    private val accounts: HashMap<String, String> = HashMap()

    fun checkValidUser(username: String, password: String): Boolean {
        return if (CommonUtils.isEMailValid(username)) {
            accounts.containsKey(username) && accounts[username] == password
        } else false
    }

    fun checkRoomAvailability(userToken: String, timeInMillis: Int, duration: Int, roomName: String?): Int {
        return ConstRequestResult.RE_AVAILABLE
    }

    companion object {
        @JvmStatic
		  var instance: ServerDummy? = null
            get() {
                if (field == null) {
                    field = ServerDummy()
                }
                return field
            }
            private set
        const val USER_TOKEN = "user_token"
    }

    init {
        val password = "password"
        accounts["peter@fh-bielefeld.de"] = password
        accounts["karl@fh-bielefeld.de"] = password
        accounts["kala@fh-bielefeld.de"] = password
    }
}