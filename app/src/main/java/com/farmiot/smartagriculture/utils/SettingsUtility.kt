package com.farmiot.smartagriculture.utils

import android.content.Context
import android.content.SharedPreferences

class SettingsUtility(context: Context) {

    private val sPreferences: SharedPreferences

    fun clearAllData() {
        userId = ""
        userEmail = ""
    }


    var userId: String
        get() = sPreferences.getString(KEY_USER_ID, null) ?: ""
        set(value) {
            setPreference(KEY_USER_ID, value)
        }
    var userEmail: String
        get() = sPreferences.getString(KEY_USER_EMAIL, null) ?: ""
        set(value) {
            setPreference(KEY_USER_EMAIL, value)
        }
    var motorStatus: Int
        get() = sPreferences.getInt(KEY_MOTOR_STATUS, 0) ?: 0
        set(value) {
            setPreference(KEY_MOTOR_STATUS, value)
        }
    var manualModeStatus: Int
        get() = sPreferences.getInt(KEY_MANUAL_MODE_STATUS, 0) ?: 0
        set(value) {
            setPreference(KEY_MANUAL_MODE_STATUS, value)
        }

    private fun setPreference(key: String, value: String) {
        val editor = sPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun setPreference(key: String, value: Int) {
        val editor = sPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }


    private fun setPreference(key: String, value: Long) {
        val editor = sPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    private fun setPreference(key: String, state: Boolean) {
        val editor = sPreferences.edit()
        editor.putBoolean(key, state)
        editor.apply()
    }

    companion object {
        private const val SHARED_PREFERENCES_FILE_NAME = "configs"
        const val KEY_USER_ID = "user_id_key"
        const val KEY_USER_EMAIL = "user_email_key"
        const val KEY_MOTOR_STATUS = "motor_status_key"
        const val KEY_MANUAL_MODE_STATUS = "manual_mode_status_key"
    }

    init {
        sPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }
}