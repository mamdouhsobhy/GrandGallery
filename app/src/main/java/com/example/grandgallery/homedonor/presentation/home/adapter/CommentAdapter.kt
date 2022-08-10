package com.example.grandgallery.homedonor.presentation.home.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.grand.homedonor.data.responseremote.getposts.Comment
import com.example.grandgallery.core.presentation.common.SharedPrefs
import com.example.grandgallery.core.presentation.extensions.layoutInflater
import com.example.grandgallery.databinding.ItemPersonCommentsBinding
import java.util.*


class CommentAdapter(private val itemSelectedForDeleteComment: (commentId:Int?,pos:Int) -> Unit) :
    ListAdapter<Comment, RecyclerView.ViewHolder>(
        diffCallback
    ) {
    lateinit var sharedPrefs: SharedPrefs
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            ItemPersonCommentsBinding.inflate(parent.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        getItem(position)?.let {
            (holder as ViewHolder).bind(it)
        }
    }

    private inner class ViewHolder(private val binding: ItemPersonCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: Comment) = with(binding) {
            sharedPrefs= SharedPrefs(binding.root.context)
            binding.tvName.text=item.userName
            binding.tvComment.text=item.content
            binding.layputComments.setOnLongClickListener{
                    binding.deleteComment.isVisible=true
                true
            }

            binding.layputComments.setOnClickListener {
                binding.deleteComment.isVisible=false
            }

            binding.deleteComment.setOnClickListener {
                itemSelectedForDeleteComment.invoke(item.id,adapterPosition)
            }
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(
                oldItem: Comment,
                newItem: Comment
            ): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(
                oldItem: Comment,
                newItem: Comment
            ): Boolean {
                return oldItem== newItem
            }
        }
    }

  

}