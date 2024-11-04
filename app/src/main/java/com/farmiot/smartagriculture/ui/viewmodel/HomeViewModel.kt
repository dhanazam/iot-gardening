package com.farmiot.smartagriculture.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.farmiot.smartagriculture.model.GardenInfo
import com.farmiot.smartagriculture.repository.MainRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(val mainRepository: MainRepository) : CoroutineViewModel(Dispatchers.IO) {

    val TAG = "HomeViewModel"
    private val _gardenInfo = MutableLiveData<GardenInfo?>()

    val gardenInfo: LiveData<GardenInfo?>
        get() = _gardenInfo

    fun updateData(newValue: GardenInfo?) {
        _gardenInfo.value = newValue
    }

    fun updateManualModeData(newValue: Int) {
        mainRepository.setManualMode(manualMode = newValue) {
            _gardenInfo.value?.info?.apply {
                manual?.manualMode = it?.manualMode ?: 0
                Log.d(TAG, "updateManualModeData: is ${it?.manualMode}")
            }
        }
        Log.d(TAG, "updateManualModeData: ${_gardenInfo.value}")
    }
    fun updateMotorStatusData(newValue: Int) {
        _gardenInfo.value?.info?.apply {
            controller?.motorStatus = newValue
        }
        Log.d(TAG, "updateMotorStatusData: ${_gardenInfo.value}")
    }

    init {
        Log.d("TAG", "HomeViewModel initiated: ")
        mainRepository.getGardenInfo {
            updateData(it)
        }
    }
}