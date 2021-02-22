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

    init {
        addActionToIntent()
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val action = intent.action
                Log.i("ActionXXX", action.toString())
                //TODO here will be new devices getting!!!
                val newDevices = arrayListOf<Device>(Device("TEst device"), Device("TEst device 2"))
                broadcastListener.onNewDevices(newDevices)
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
}