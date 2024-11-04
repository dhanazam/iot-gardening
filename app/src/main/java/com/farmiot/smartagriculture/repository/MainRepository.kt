package com.farmiot.smartagriculture.repository

import android.util.Log
import com.farmiot.smartagriculture.model.GardenInfo
import com.farmiot.smartagriculture.model.Manual
import com.farmiot.smartagriculture.utils.SettingsUtility
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainRepository() : KoinComponent {

    val settingsUtility by inject<SettingsUtility>()
    val TAG = "MainRepository"

    // Write a message to the database
    val database = Firebase.database
    val myRef = database.getReference("Garden Info")

    fun getGardenInfo(
        uid: String = settingsUtility.userId,
        onComplete: (data: GardenInfo?) -> Unit
    ) {
        val childNodeRef = myRef.child(uid)
        Log.d(TAG, "uid: $uid\ndatabase: $childNodeRef")

        childNodeRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Data has been retrieved successfully
                Log.d(TAG, "onDataChange: ${dataSnapshot.value}")
                val data = dataSnapshot.getValue(GardenInfo::class.java)

                Log.d(
                    TAG, "onDataChange: \n" +
                            "manual = ${data?.info?.manual?.manualMode}\n" +
                            "weather = ${data?.info?.weather}\n" +
                            "controller = ${data?.info?.controller?.motorStatus}\n" +
                            "weatherReport = ${data?.info?.weatherReport?.size}\n" +
                            ""
                )
                onComplete(data)
                //onComplete(result.body()?.data)
            }

            override fun onCancelled(error: DatabaseError) {
                // An error occurred
                Log.e(TAG, "Data retrieval failed: ${error.message}")
            }
        })
    }

    fun setManualMode(
        uid: String = settingsUtility.userId,
        manualMode: Int = 0,
        onComplete: (data: Manual?) -> Unit
    ) {
        val manualMode = Manual(manualMode)
        val childNodeRef = myRef.child(uid)
        Log.d(TAG, "uid: $uid\ndatabase: $childNodeRef")
        childNodeRef.child("info").child("manual").setValue(manualMode)
            .addOnSuccessListener {
                // Write was successful!
                onComplete(manualMode)
                Log.d(TAG, "onDataChange:  successful ${it}")
            }
            .addOnFailureListener {
                // Write failed
                onComplete(null)
                Log.d(TAG, "onDataChange: failed ${it.message}")
            }
    }

}