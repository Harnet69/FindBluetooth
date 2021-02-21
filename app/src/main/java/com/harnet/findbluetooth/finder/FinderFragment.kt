package com.harnet.findbluetooth.finder

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.harnet.findbluetooth.R
import com.harnet.findbluetooth.databinding.FinderFragmentBinding

class FinderFragment : Fragment() {
    private lateinit var viewModel: FinderViewModel
    private lateinit var dataBinding: FinderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(FinderViewModel::class.java)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.finder_fragment, container, false)

        return dataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        dataBinding.btnSearch.setOnClickListener {
            viewModel.refresh()
        }
    }

    private fun observeViewModel(){
        viewModel.mDeviceList.observe(viewLifecycleOwner, { devicesList ->
            Log.i("DevicesList", "observeViewModel: $devicesList")
            Toast.makeText(context, devicesList.toString(), Toast.LENGTH_LONG).show()
        })
    }


}