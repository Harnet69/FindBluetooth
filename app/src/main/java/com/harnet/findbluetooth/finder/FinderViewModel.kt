package com.harnet.findbluetooth.finder

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.harnet.findbluetooth.BaseViewModel
import com.harnet.findbluetooth.model.Device
import com.harnet.findbluetooth.repository.BluetoothFinderRepositoryInterface
import kotlinx.coroutines.runBlocking

class FinderViewModel @ViewModelInject constructor(private val bluetoothFinderRepo: BluetoothFinderRepositoryInterface, application: Application) : BaseViewModel(application) {
//    private val bluetoothFinderRepo = BluetoothFinderRepository()

    val mDeviceList = MutableLiveData<ArrayList<Device>>()
    val mIsSearching = MutableLiveData<Boolean>()
    val mSearchingError = MutableLiveData<String>()


    fun refresh(finderFragment: FinderFragment) {
        listenToRepo(finderFragment)
        runBlocking {
            bluetoothFinderRepo.searchDevices()
        }
    }

    fun registerReceiver() {
        bluetoothFinderRepo.registerReceiver(getApplication())
    }

    fun unRegisterReceiver() {
        bluetoothFinderRepo.unRegisterReceiver(getApplication())
    }

    // listen to new bluetooth devices
    private fun listenToRepo(finderFragment: FinderFragment) {
        bluetoothFinderRepo.mDevicesList.observe(finderFragment, { newDevices ->
            if (newDevices.isNotEmpty()) {
                mDeviceList.value = newDevices
            } else {
                mIsSearching.value = false
                mSearchingError.value = "No device find"
            }
        })
    }
}