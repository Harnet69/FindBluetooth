package com.harnet.findbluetooth

import android.content.Context
import com.harnet.findbluetooth.model.Device
import com.harnet.findbluetooth.repository.BluetoothFinderRepositoryInterface

class FakeBluetoothFinderRepository: BluetoothFinderRepositoryInterface {
    override fun searchDevices() {
        mDevicesList.postValue(arrayListOf(Device("device1", "192.168.0.1", -81),
                                           Device("device2", "192.168.0.2", -61),
                                           Device("device3", "192.168.0.3", -41)))
    }

    override fun registerReceiver(context: Context) {
        TODO("Not yet implemented")
    }

    override fun unRegisterReceiver(context: Context) {
        TODO("Not yet implemented")
    }
}