package com.harnet.findbluetooth.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.harnet.findbluetooth.helper.BroadcastHelper
import com.harnet.findbluetooth.model.Device

class BluetoothFinderRepository {
    private val broadcastHelper = BroadcastHelper()
    val mDevicesList = MutableLiveData<ArrayList<Device>>()

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
                Log.i("ActionXXX", "onNewDevices: $newDevices")
                mDevicesList.value = newDevices
            }
        }
    }
}