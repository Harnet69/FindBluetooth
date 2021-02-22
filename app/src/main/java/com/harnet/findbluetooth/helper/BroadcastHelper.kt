package com.harnet.findbluetooth.helper

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.harnet.findbluetooth.model.Device

class BroadcastHelper {
    var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    var intentFilter: IntentFilter = IntentFilter()
    var broadcastReceiver: BroadcastReceiver

    lateinit var broadcastListener: BroadcastListener

    private val newDevices = arrayListOf<Device>()

    init {
        addActionToIntent()
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                whenReceived(intent)
            }
        }
    }

    private fun addActionToIntent() {
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
    }

    interface BroadcastListener {
        fun onNewDevices(newDevices: ArrayList<Device>)
    }

    fun setListener(l: BroadcastListener) {
        broadcastListener = l
    }

    private fun whenReceived(intent: Intent) {
        val action = intent.action
        if (BluetoothDevice.ACTION_FOUND == action) {
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)
            Log.i("ActionXXX", "whenReceived: ${device?.type}")
            newDevices.add(Device(device?.name, device?.address, rssi.toInt()))

        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
//            val newDevices = arrayListOf<Device>(Device("TEst device"), Device("TEst device 2"))
            Log.i("ActionXXX", "whenReceived: $newDevices")
            broadcastListener.onNewDevices(newDevices)
        }
    }
}