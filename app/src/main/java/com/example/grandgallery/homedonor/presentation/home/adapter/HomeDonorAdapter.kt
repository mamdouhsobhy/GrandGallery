package com.example.grand.homedonor.presentation.home.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.grand.homedonor.data.responseremote.getposts.Comment
import com.example.grand.homedonor.data.responseremote.getposts.ModelGetAdminPostResponseRemote
import com.example.grandgallery.core.presentation.common.SharedPrefs
import com.example.grandgallery.core.presentation.extensions.layoutInflater
import com.example.grandgallery.core.presentation.extensions.loadImage
import com.example.grandgallery.databinding.ItemPostsBinding
import java.time.format.DateTimeFormatter


class HomeDonorAdapter(private val itemSelectedForComment: (item:List<Comment>?,postId:Int) -> Unit,
                       private val itemSelectedForLike: (postId:Int?) -> Unit,
                       private val itemSelectedForDisLike: (likeId:Int?) -> Unit) :
    ListAdapter<ModelGetAdminPostResponseRemote, RecyclerView.ViewHolder>(
        diffCallback
    ) {
    lateinit var sharedPrefs: SharedPrefs
     var listLikes:ArrayList<String>?=null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            ItemPostsBinding.inflate(parent.layoutInflater, parent, false)
        return ViewHolder(binding)
    }
    var likeId=0
    @SuppressLint("NewApi")
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        getItem(position)?.let {
            (holder as ViewHolder).bind(it)
        }
    }

    private inner class ViewHolder(private val binding: ItemPostsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: ModelGetAdminPostResponseRemote) = with(binding) {
            sharedPrefs= SharedPrefs(binding.root.context)
            val date = item.date
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val d=formatter.parse(date)
            val newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a")
            val formatted = newFormat?.format(d)
            binding.txtDate.text=formatted
            binding.txtCaption.text=item.content
            if(item.imageName!!.isEmpty()){
                binding.imgPost.isVisible=false
            }else{
                binding.imgPost.isVisible=true
                binding.imgPost.loadImage(root.context,
                    item.imageName
                )
            }

            if(item.likes?.size!=0) {
                binding.txtNumLikes.text = item.likes?.size.toString()

            }else{
                binding.txtNumLikes.text = "0"
            }


            binding.imgComment.setOnClickListener {
                item.id?.let { it1 -> itemSelectedForComment.invoke(item.comments, it1) }
            }

            binding.imgLike.setOnClickListener {
                if(item.likes?.size!=0){
                    var liked=false
                    for(i in 0 until item.likes?.size!!){

                        if (item.likes[i].applicationUserId==sharedPrefs.getID()){
                                likeId= item.likes[i].id!!
                                liked=true
                            break
                        }else{
                            //like
                              liked=false

                        }

                    }
                    if(liked){
                        itemSelectedForDisLike.invoke(likeId)
                    }else{
                        itemSelectedForLike.invoke(item.id)
                    }

                }else{
                    itemSelectedForLike.invoke(item.id)
                }

            }
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ModelGetAdminPostResponseRemote>() {
            override fun areItemsTheSame(
                oldItem: ModelGetAdminPostResponseRemote,
                newItem: ModelGetAdminPostResponseRemote
            ): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(
                oldItem: ModelGetAdminPostResponseRemote,
                newItem: ModelGetAdminPostResponseRemote
            ): Boolean {
                return oldItem== newItem
            }
        }
    }

  

}