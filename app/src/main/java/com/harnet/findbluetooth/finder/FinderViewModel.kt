package com.harnet.findbluetooth.finder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harnet.findbluetooth.model.Device
import kotlinx.coroutines.*

class FinderViewModel : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    val mDeviceList = MutableLiveData<ArrayList<Device>>()
    val mIsSearching = MutableLiveData<Boolean>()
    val mSearchingError = MutableLiveData<String>()

    fun refresh() {
        searchDevices()
    }

    private fun searchDevices() {
        mIsSearching.value = true
        coroutineScope.launch {
            val newDevicesList = arrayListOf<Device>(
                Device("testDevice1"),
                Device("testDevice2"),
                Device("testDevice3"),
            )
            delay(2000L)
            coroutineScope.launch(Dispatchers.Main) {
                if (newDevicesList.isNotEmpty()) {
                    mDeviceList.value = newDevicesList
                } else {
                    mIsSearching.value = false
                    mSearchingError.value = "No device find"
                }
            }
        }
    }

}