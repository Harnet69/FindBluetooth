package com.harnet.findbluetooth.finder

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harnet.findbluetooth.model.Device
import com.harnet.findbluetooth.repository.BluetoothFinderRepositoryInterface
import kotlinx.coroutines.runBlocking

class FinderViewModel @ViewModelInject constructor(private val repo: BluetoothFinderRepositoryInterface) : ViewModel() {
    val mDeviceList = MutableLiveData<ArrayList<Device>>()
    val mIsSearching = MutableLiveData<Boolean>()
    val mSearchingError = MutableLiveData<String>()


    fun refresh(finderFragment: FinderFragment) {
        listenToRepo(finderFragment)
        runBlocking {
            repo.searchDevices()
        }
    }

    fun registerReceiver(context: Context) {
        repo.registerReceiver(context)
    }

    fun unRegisterReceiver(context: Context) {
        repo.unRegisterReceiver(context)
    }

    // listen to new bluetooth devices
    private fun listenToRepo(finderFragment: FinderFragment) {
        repo.mDevicesList.observe(finderFragment, { newDevices ->
            if (newDevices.isNotEmpty()) {
                mDeviceList.value = newDevices
            } else {
                mIsSearching.value = false
                mSearchingError.value = "No device find"
            }
        })
    }
}