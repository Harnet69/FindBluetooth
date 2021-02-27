package com.harnet.findbluetooth.repository

import android.content.Context
import android.util.Log
import com.harnet.findbluetooth.helper.BroadcastHelper
import com.harnet.findbluetooth.model.Device

class BluetoothFinderRepository {
    private val broadcastHelper = BroadcastHelper()

    init {
        broadcastHelper.setListener(getBroadcastListener())
    }

    fun searchDevices() {
        broadcastHelper.searchDevices()
    }

    fun registerReceiver(context: Context) {
        context.registerReceiver(
            broadcastHelper.broadcastReceiver,
            broadcastHelper.intentFilter
        )
    }

    fun unRegisterReceiver(context: Context) {
        context.unregisterReceiver(broadcastHelper.broadcastReceiver)
    }

    // listen to new bluetooth devices
    private fun getBroadcastListener(): BroadcastHelper.BroadcastListener {
        return object : BroadcastHelper.BroadcastListener {
            override fun onNewDevices(newDevices: ArrayList<Device>) {
                if (newDevices.isNotEmpty()) {
                    Log.i("ActionXXX", "onNewDevices: $newDevices")
//                    mDeviceList.value = newDevices
                } else {
                    Log.i("ActionXXX", "onNewDevices: No Device Found")
//                    mIsSearching.value = false
//                    mSearchingError.value = "No device find"
                }
            }
        }
    }
}