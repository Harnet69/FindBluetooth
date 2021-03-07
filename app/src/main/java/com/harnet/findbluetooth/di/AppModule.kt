package com.harnet.findbluetooth.di

import com.harnet.findbluetooth.helper.BroadcastHelper
import com.harnet.findbluetooth.repository.BluetoothFinderRepository
import com.harnet.findbluetooth.repository.BluetoothFinderRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectBluetoothFinderRepository(broadcastHelper: BroadcastHelper) = BluetoothFinderRepository(broadcastHelper) as BluetoothFinderRepositoryInterface
}