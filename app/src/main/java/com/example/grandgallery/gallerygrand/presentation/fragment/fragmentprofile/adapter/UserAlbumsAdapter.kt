package com.example.grandgallery.gallerygrand.presentation.fragment.fragmentprofile.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.grandgallery.core.presentation.extensions.layoutInflater
import com.example.grandgallery.databinding.LayoutItemAlbumsBinding
import com.example.grandgallery.gallerygrand.data.responseremote.getalbums.ModelGetAlbumsResponseRemoteItem


class UserAlbumsAdapter(
    val itemSelectedForActions: (model: ModelGetAlbumsResponseRemoteItem) -> Unit) :
    ListAdapter<ModelGetAlbumsResponseRemoteItem, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            LayoutItemAlbumsBinding.inflate(parent.layoutInflater, parent, false)
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

    private inner class ViewHolder(private val binding: LayoutItemAlbumsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ShowToast")
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(item: ModelGetAlbumsResponseRemoteItem) = with(binding) {

            binding.tvAlbumName.text=item.title

            binding.tvAlbumName.setOnClickListener {
                itemSelectedForActions.invoke(item)
            }
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ModelGetAlbumsResponseRemoteItem>() {
            override fun areItemsTheSame(
                oldItem: ModelGetAlbumsResponseRemoteItem,
                newItem: ModelGetAlbumsResponseRemoteItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ModelGetAlbumsResponseRemoteItem,
                newItem: ModelGetAlbumsResponseRemoteItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}