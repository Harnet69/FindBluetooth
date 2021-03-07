package com.harnet.findbluetooth.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.harnet.findbluetooth.model.Device

interface BluetoothFinderRepositoryInterface {
    val mDevicesList: MutableLiveData<ArrayList<Device>>
        get() = MutableLiveData<ArrayList<Device>>()

    fun searchDevices()

    fun registerReceiver(context: Context)

    fun unRegisterReceiver(context: Context)
}