package com.ilagoproject.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilagoproject.myapplication.R
import com.ilagoproject.myapplication.apiservice.data.DataSignal
import com.ilagoproject.myapplication.databinding.ViewDataSignalBinding
import java.util.*

class DataSignalAdapter : RecyclerView.Adapter<DataSignalAdapter.ViewHolder>() {

    private val items = LinkedList<DataSignal>()

    fun setData(data: List<DataSignal>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataSignalBinding = DataBindingUtil.inflate(inflater, R.layout.view_data_signal, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: ViewDataSignalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataSignal: DataSignal){
            binding.dataSignal = dataSignal
            binding.executePendingBindings()
        }
    }
}