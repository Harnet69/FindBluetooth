package com.harnet.findbluetooth.finder

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.harnet.findbluetooth.R
import com.harnet.findbluetooth.databinding.FinderFragmentBinding
import com.harnet.findbluetooth.helper.BroadcastHelper
import com.harnet.findbluetooth.model.Device


class FinderFragment : Fragment() {
    private lateinit var viewModel: FinderViewModel
    private lateinit var dataBinding: FinderFragmentBinding
    private lateinit var finderAdapter: FinderAdapter
    private val broadcastHelper = BroadcastHelper()

    var bluetoothAdapter: BluetoothAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(FinderViewModel::class.java)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.finder_fragment, container, false)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        finderAdapter = FinderAdapter(arrayListOf())

        observeViewModel()

        dataBinding.finderBtnSearch.setOnClickListener {
            bluetoothAdapter?.startDiscovery()
            viewModel.refresh()
        }

        dataBinding.finderDevicesList.apply {
            layoutManager = LinearLayoutManager(context)
            //Fix blinking RecyclerView
//            feedsAdapter.setHasStableIds(true)
            adapter = finderAdapter
        }

        // add separation line between items
        dataBinding.finderDevicesList.addItemDecoration(
            DividerItemDecoration(
                dataBinding.finderDevicesList.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun observeViewModel() {
        viewModel.mDeviceList.observe(viewLifecycleOwner, { devicesList ->
            inFound(devicesList)
        })

        viewModel.mIsSearching.observe(viewLifecycleOwner, { isSearching ->
            if (isSearching) {
                inSearch()
            }
        })

        viewModel.mSearchingError.observe(viewLifecycleOwner, { eMsg ->
            inError(eMsg)
        })
    }

    // while searching
    private fun inSearch() {
        dataBinding.finderDevicesList.visibility = View.INVISIBLE
        dataBinding.finderSearchStatus.visibility = View.VISIBLE
        dataBinding.finderLoadProgressBar.visibility = View.VISIBLE
        dataBinding.finderBtnSearch.isClickable = false
    }

    // when devices were found
    private fun inFound(devicesList: ArrayList<Device>) {
        dataBinding.finderDevicesList.visibility = View.VISIBLE
        dataBinding.finderSearchStatus.visibility = View.INVISIBLE
        dataBinding.finderLoadProgressBar.visibility = View.INVISIBLE
        dataBinding.finderBtnSearch.isClickable = true
        finderAdapter.updateDevicesList(devicesList)
//        Toast.makeText(context, devicesList.toString(), Toast.LENGTH_LONG).show()
    }

    // when en error occur
    private fun inError(eMsg: String) {
        dataBinding.finderDevicesList.visibility = View.INVISIBLE
        dataBinding.finderSearchStatus.visibility = View.INVISIBLE
        dataBinding.finderLoadProgressBar.visibility = View.INVISIBLE
        dataBinding.finderErrorMsg.text = eMsg
        dataBinding.finderErrorMsg.visibility = View.VISIBLE
        dataBinding.finderBtnSearch.isClickable = true
    }

    override fun onStart() {
        super.onStart()
        activity?.registerReceiver(broadcastHelper.broadcastReceiver, broadcastHelper.intentFilter)
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(broadcastHelper.broadcastReceiver)
    }
}