package com.harnet.findbluetooth.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.harnet.findbluetooth.helper.BroadcastHelper
import com.harnet.findbluetooth.model.Device
import javax.inject.Inject

class BluetoothFinderRepository @Inject constructor(private val broadcastHelper: BroadcastHelper) :
    BluetoothFinderRepositoryInterface {
    override val mDevicesList = MutableLiveData<ArrayList<Device>>()

    init {
        broadcastHelper.setListener(getBroadcastListener())
    }

    override fun searchDevices() {
        broadcastHelper.searchDevices()
    }

    override fun registerReceiver(context: Context) {
        context.registerReceiver(
            broadcastHelper.broadcastReceiver,
            broadcastHelper.intentFilter
        )
    }

    override fun unRegisterReceiver(context: Context) {
        context.unregisterReceiver(broadcastHelper.broadcastReceiver)
    }

    // listen to new bluetooth devices
    private fun getBroadcastListener(): BroadcastHelper.BroadcastListener {
        return object : BroadcastHelper.BroadcastListener {
            override fun onNewDevices(newDevices: ArrayList<Device>) {
                mDevicesList.value = newDevices
            }
        }
    }
}