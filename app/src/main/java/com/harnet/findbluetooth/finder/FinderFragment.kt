package com.harnet.findbluetooth.finder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.harnet.findbluetooth.R
import com.harnet.findbluetooth.databinding.FinderFragmentBinding
import com.harnet.findbluetooth.model.Device

class FinderFragment : Fragment() {
    private lateinit var viewModel: FinderViewModel
    private lateinit var dataBinding: FinderFragmentBinding
    private lateinit var finderAdapter: FinderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(FinderViewModel::class.java)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.finder_fragment, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        finderAdapter = FinderAdapter(arrayListOf())

        observeViewModel()

        // start searching
        dataBinding.finderBtnSearch.setOnClickListener {
            if (context?.let { context ->
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } == PackageManager.PERMISSION_GRANTED) {
                inSearch()
                viewModel.refresh(this)
            } else {
                //ask for permission
                activity?.let { activity ->
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayListOf(Manifest.permission.ACCESS_COARSE_LOCATION).toTypedArray(), 101
                    )
                }
            }
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
        viewModel.registerReceiver()
    }

    override fun onStop() {
        super.onStop()
        viewModel.unRegisterReceiver()
    }
}