package com.harnet.findbluetooth.finder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.harnet.findbluetooth.BaseViewModel
import com.harnet.findbluetooth.helper.BroadcastHelper
import com.harnet.findbluetooth.model.Device
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FinderViewModel(application: Application) : BaseViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val broadcastHelper = BroadcastHelper()

    val mDeviceList = MutableLiveData<ArrayList<Device>>()
    val mIsSearching = MutableLiveData<Boolean>()
    val mSearchingError = MutableLiveData<String>()

    init {
        val bl = getBroadcastListenerListener()
        broadcastHelper.setListener(bl)
    }

    fun refresh() {
        searchDevices()
    }

    private fun searchDevices() {
        mIsSearching.value = true
        coroutineScope.launch {
            //TODO start to look at for bluetooth devices
            broadcastHelper.bluetoothAdapter?.startDiscovery()
        }
    }

    fun registerReceiver() {
        getApplication<Application>().registerReceiver(
            broadcastHelper.broadcastReceiver,
            broadcastHelper.intentFilter
        )
    }

    fun unRegisterReceiver() {
        getApplication<Application>().unregisterReceiver(broadcastHelper.broadcastReceiver)
    }

    // listen to new bluetooth devices
    private fun getBroadcastListenerListener(): BroadcastHelper.BroadcastListener {
        return object : BroadcastHelper.BroadcastListener {
            override fun onNewDevices(newDevices: ArrayList<Device>) {
                if (newDevices.isNotEmpty()) {
                    mDeviceList.value = newDevices
                } else {
                    mIsSearching.value = false
                    mSearchingError.value = "No device find"
                }
            }
        }
    }

}