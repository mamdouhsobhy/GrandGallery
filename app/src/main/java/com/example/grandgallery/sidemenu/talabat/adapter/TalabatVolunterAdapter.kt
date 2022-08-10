package com.example.grandgallery.sidemenu.talabat.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.grandgallery.core.presentation.extensions.layoutInflater
import com.example.grandgallery.databinding.ItemShowOrderBinding
import com.example.grandgallery.homedonor.data.responseremote.getusers.ModelGetUsersResponseRemote

class TalabatVolunterAdapter :
    ListAdapter<ModelGetUsersResponseRemote, RecyclerView.ViewHolder>(
        diffCallback
    ) {

   lateinit var mItem:ModelGetUsersResponseRemote
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            ItemShowOrderBinding.inflate(parent.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        getItem(position)?.let {
            (holder as ViewHolder).bind(it)
        }
    }

    private inner class ViewHolder(private val binding: ItemShowOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NewApi", "SetTextI18n")
        fun bind(item: ModelGetUsersResponseRemote) = with(binding) {
            mItem=item

          binding.tvDonorNameValue.text=item.userName
          binding.tvDonorPhoneValue.text=item.phoneNumber
            binding.tvOrderDateValue.text=item.donationRate
            binding.tvNumMealValue.text= item.donationNumber.toString()+ " وجبات "
          binding.tvLocationValue.text=item.location+" ("+item.detailAddress+" )"

        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ModelGetUsersResponseRemote>() {
            override fun areItemsTheSame(
                oldItem: ModelGetUsersResponseRemote,
                newItem: ModelGetUsersResponseRemote
            ): Boolean {
                return oldItem== newItem
            }

            override fun areContentsTheSame(
                oldItem: ModelGetUsersResponseRemote,
                newItem: ModelGetUsersResponseRemote
            ): Boolean {
                return oldItem == newItem
            }
        }
    }



}