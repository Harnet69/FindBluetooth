package com.harnet.findbluetooth.helper

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

class BroadcastHelper {
    var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    var intentFilter: IntentFilter = IntentFilter()
    var broadcastReceiver: BroadcastReceiver

    init {
        addActionToIntent()
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                actionWhenReceive(context, intent)
            }
        }
    }

    private fun addActionToIntent(){
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
    }

    private fun actionWhenReceive(context: Context?, intent: Intent){
        val action = intent.action
        Log.i("AcXXXtion", action.toString())
//            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
//                statusTextView.setText("Finished")
//                searchButton.setEnabled(true)
//            }
    }

}