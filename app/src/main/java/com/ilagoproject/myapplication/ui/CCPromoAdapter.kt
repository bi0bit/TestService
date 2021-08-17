package com.ilagoproject.myapplication.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilagoproject.myapplication.R
import com.ilagoproject.myapplication.databinding.ViewCcPromoBinding
import com.ilagoproject.myapplication.model.data.CCPromoData
import java.util.*

class CCPromoAdapter : RecyclerView.Adapter<CCPromoAdapter.ViewHolder>() {

    private var items: LinkedList<CCPromoData> = LinkedList<CCPromoData>()

    fun setData(data: Collection<CCPromoData>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding:ViewCcPromoBinding = DataBindingUtil.inflate(inflater, R.layout.view_cc_promo, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class ViewHolder(var binding: ViewCcPromoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ccPromoData: CCPromoData){
            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ccPromoData.link))
                binding.root.context.startActivity(intent)
            }
            binding.ccPromoData = ccPromoData
            binding.executePendingBindings()
        }
    }
}
