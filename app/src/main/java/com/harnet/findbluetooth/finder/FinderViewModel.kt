package com.harnet.findbluetooth.finder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.harnet.findbluetooth.BaseViewModel
import com.harnet.findbluetooth.model.Device
import com.harnet.findbluetooth.repository.BluetoothFinderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class FinderViewModel(application: Application) : BaseViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val bluetoothFinderRepo = BluetoothFinderRepository()

    val mDeviceList = MutableLiveData<ArrayList<Device>>()
    val mIsSearching = MutableLiveData<Boolean>()
    val mSearchingError = MutableLiveData<String>()

    fun refresh() {
        runBlocking {
            bluetoothFinderRepo.searchDevices()
        }
    }

    fun registerReceiver() {
        bluetoothFinderRepo.registerReceiver(getApplication())
    }

    fun unRegisterReceiver() {
        bluetoothFinderRepo.unRegisterReceiver(getApplication())
    }

    // listen to new bluetooth devices
    private fun listenToFind(){

    }
//    private fun getBroadcastListenerListener(): BroadcastHelper.BroadcastListener {
//        return object : BroadcastHelper.BroadcastListener {
//            override fun onNewDevices(newDevices: ArrayList<Device>) {
//                if (newDevices.isNotEmpty()) {
//                    mDeviceList.value = newDevices
//                } else {
//                    mIsSearching.value = false
//                    mSearchingError.value = "No device find"
//                }
//            }
//        }
//    }

}