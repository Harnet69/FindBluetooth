package com.harnet.findbluetooth.finder

import com.harnet.findbluetooth.FakeBluetoothFinderRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

@ExperimentalCoroutinesApi
class FinderViewModelTest {
    private lateinit var viewModel: FinderViewModel

    @Before
    fun setup(){
        viewModel = FinderViewModel(FakeBluetoothFinderRepository())
    }
}