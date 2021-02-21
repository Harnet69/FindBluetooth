package com.harnet.findbluetooth.finder

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
}