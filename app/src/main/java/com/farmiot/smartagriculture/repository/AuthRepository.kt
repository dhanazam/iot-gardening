package com.farmiot.smartagriculture.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.farmiot.smartagriculture.utils.SettingsUtility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthAppRepository(
    private val application: Application,
    val settingsUtility: SettingsUtility
) {
    private val firebaseAuth: FirebaseAuth
    val userLiveData: MutableLiveData<FirebaseUser?>
    val loggedOutLiveData: MutableLiveData<Boolean>

    fun login(email: String?, password: String?) {
        firebaseAuth.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                    settingsUtility.userId = firebaseAuth.currentUser?.uid.toString()
                    settingsUtility.userEmail = firebaseAuth.currentUser?.email.toString()
                    Log.d("AuthTAG", "authAppRepository: ${firebaseAuth.currentUser?.uid}")
                } else {
                    Toast.makeText(
                        application.applicationContext,
                        "Login Failure: " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    userLiveData.postValue(null)
                }
            }
    }


    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
        userLiveData.postValue(null)
        settingsUtility.clearAllData()
    }

    init {
        firebaseAuth = FirebaseAuth.getInstance()
        userLiveData = MutableLiveData()
        loggedOutLiveData = MutableLiveData()
        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser)
            settingsUtility.userId = firebaseAuth.currentUser?.uid.toString()
            settingsUtility.userEmail = firebaseAuth.currentUser?.email.toString()
            Log.d("AuthTAG", "authAppRepository: ${firebaseAuth.currentUser}")
            Log.d("AuthTAG", "authAppRepository: ${firebaseAuth.currentUser?.uid}")
            loggedOutLiveData.postValue(false)
        } else {
            userLiveData.postValue(null)
            loggedOutLiveData.postValue(true)
        }
    }
}