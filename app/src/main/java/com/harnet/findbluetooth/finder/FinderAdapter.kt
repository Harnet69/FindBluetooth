package com.harnet.findbluetooth.finder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.harnet.findbluetooth.R
import com.harnet.findbluetooth.databinding.ItemDeviceBinding
import com.harnet.findbluetooth.model.Device

class FinderAdapter(private var devicesList: ArrayList<Device>):
RecyclerView.Adapter<FinderAdapter.ImagesViewHolder>(){

    //for updating information from a backend
    fun updateDevicesList(newDevicesList: ArrayList<Device>) {
        if(newDevicesList.isNotEmpty()) {
            // get new parsed articles
            devicesList.clear()
            devicesList.addAll(newDevicesList)
            //reset RecycleView and recreate a list
            notifyDataSetChanged()
        }
    }

    class ImagesViewHolder(var view: ItemDeviceBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // elements of the list transforms into views. DataBinding approach
        val view = DataBindingUtil.inflate<ItemDeviceBinding>(
            inflater,
            R.layout.item_device,
            parent,
            false
        )
        return ImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.view.device= devicesList[position]
    }

    override fun getItemCount(): Int {
        return devicesList.size
    }

}