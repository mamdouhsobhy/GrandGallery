package com.example.grandgallery.gallerygrand.presentation.fragment.fragmentphoto.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.grandgallery.core.presentation.extensions.layoutInflater
import com.example.grandgallery.databinding.LayoutImageUserAlbumsBinding
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemoteItem
import com.example.grandgallery.gallerygrand.data.responseremote.getphotos.ModelGetPhotosResponseRemoteItem


class UserPhotosAdapter(val itemSelectedForActions: (model: ModelGetPhotosResponseRemoteItem) -> Unit) :
    ListAdapter<ModelGetPhotosResponseRemoteItem, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            LayoutImageUserAlbumsBinding.inflate(parent.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        getItem(position)?.let {
            (holder as ViewHolder).bind(it)
        }
    }

    private inner class ViewHolder(private val binding: LayoutImageUserAlbumsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ShowToast")
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(item: ModelGetPhotosResponseRemoteItem) = with(binding) {

            Glide.with(binding.root.context).load(item.url+".png").into(binding.imgAlbumUser)

            binding.imgAlbumUser.setOnClickListener {
                itemSelectedForActions.invoke(item)
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ModelGetPhotosResponseRemoteItem>() {
            override fun areItemsTheSame(
                oldItem: ModelGetPhotosResponseRemoteItem,
                newItem: ModelGetPhotosResponseRemoteItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ModelGetPhotosResponseRemoteItem,
                newItem: ModelGetPhotosResponseRemoteItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}