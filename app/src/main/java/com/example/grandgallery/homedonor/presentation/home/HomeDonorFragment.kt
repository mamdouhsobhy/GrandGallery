package com.example.grandgallery.homedonor.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.grand.homedonor.data.responseremote.addcomment.ModelAddCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.addlike.ModelAddLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.deletecomment.ModelDeleteCommentResponseRemote
import com.example.grand.homedonor.data.responseremote.deletelike.ModelDeleteLikeResponseRemote
import com.example.grand.homedonor.data.responseremote.getposts.Comment
import com.example.grand.homedonor.data.responseremote.getposts.ModelGetAdminPostResponseRemote
import com.example.grand.homedonor.domain.requestremote.ModelAddComment
import com.example.grand.homedonor.domain.requestremote.ModelAddLike
import com.example.grandgallery.homedonor.presentation.home.adapter.CommentAdapter
import com.example.grand.homedonor.presentation.home.adapter.HomeDonorAdapter
import com.example.grandgallery.R
import com.example.grandgallery.core.presentation.base.BaseFragmentBinding
import com.example.grandgallery.core.presentation.common.SharedPrefs
import com.example.grandgallery.core.presentation.extensions.showGenericAlertDialog
import com.example.grandgallery.core.presentation.extensions.showToast
import com.example.grandgallery.databinding.FragmentHomeDonorBinding
import com.example.grandgallery.databinding.LayoutCommentsBinding
import com.example.grandgallery.homedonor.presentation.home.viewmodel.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class HomeDonorFragment : BaseFragmentBinding<FragmentHomeDonorBinding>() {
    lateinit var listComment: ArrayList<Comment>
    var postID = 0
    var position = 0
    private val adapterGetPosts: HomeDonorAdapter by lazy {
        HomeDonorAdapter(itemSelectedForComment = { it, postId ->
            postID = postId
            setupComments(it)
            bottomSheetCommentsDialog.show()
        }, itemSelectedForLike = {
            viewModel.isLikedAddedLoaded = false
            val modelAddLike = ModelAddLike(it, sharedPrefs.getID())
            viewModel.addLike(modelAddLike)

        }, itemSelectedForDisLike = {
           viewModel.isLikedDeletedLoaded=false
            viewModel.deleteLike(it.toString())
        })
    }
    private val adapterComments: CommentAdapter by lazy {
        CommentAdapter(itemSelectedForDeleteComment = { it, pos ->
            position = pos
            viewModel.isCommentDeletedLoaded = false
            viewModel.deleteComment(it.toString())
        })
    }
    val viewModel: HomeDonorViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeDonorViewModel::class.java]
    }

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private lateinit var bottomSheetCommentsDialog: BottomSheetDialog
    lateinit var mbindingComments: LayoutCommentsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            viewModel.getPosts(sharedPrefs.getID())

        addListenerOnView()
        observeStateFlow()
        setUpPosts()
        showBottomSheet()
    }

    private fun addListenerOnView() {
        binding.swipeToRefresh.setOnRefreshListener {

            if (viewModel.isScreenLoaded.not()) {
                viewModel.getPosts(sharedPrefs.getID())
            } else {
                binding.swipeToRefresh.isRefreshing = false
            }
        }
    }

    private fun setUpPosts() {
        binding.rvPosts.adapter = adapterGetPosts
    }

    private fun observeStateFlow() {
        viewModel.getPostsState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.addCommentState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChangeAddComment(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.deletCommentState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChangeDeleteComment(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.addLikedState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChangeAddLike(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.deleteLikedState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChangeDeleteLike(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleStateChange(state: HomeDonorActivityState) {
        when (state) {
            is HomeDonorActivityState.Init -> Unit
            is HomeDonorActivityState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is HomeDonorActivityState.Success -> handleSuccess(state.modelGetPosts)
            is HomeDonorActivityState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is HomeDonorActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleSuccess(posts: List<ModelGetAdminPostResponseRemote>) {
        adapterGetPosts.notifyDataSetChanged()
        adapterGetPosts.submitList(posts)
        viewModel.isScreenLoaded = true
        binding.swipeToRefresh.isRefreshing = false
    }

    //region addcomment
    private fun handleStateChangeAddComment(state: AddCommentActivityState) {
        when (state) {
            is AddCommentActivityState.Init -> Unit
            is AddCommentActivityState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is AddCommentActivityState.Success -> handleSuccessAddComment(state.modelAddComment)
            is AddCommentActivityState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is AddCommentActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleSuccessAddComment(posts: ModelAddCommentResponseRemote) {
        if (viewModel.isCommentAddedLoaded.not()) {
            viewModel.getPosts(sharedPrefs.getID())
            val comment: Comment = Comment(
                sharedPrefs.getID(),
                posts.content, "", posts.commentId, postID, sharedPrefs.getName()
            )
            listComment.add(comment)
            setupComments(listComment)
        }
        viewModel.isCommentAddedLoaded = true
        binding.swipeToRefresh.isRefreshing = false
    }
    //endregion

    //region deletecomment
    private fun handleStateChangeDeleteComment(state: DeleteCommentActivityState) {
        when (state) {
            is DeleteCommentActivityState.Init -> Unit
            is DeleteCommentActivityState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is DeleteCommentActivityState.Success -> handleSuccessDeleteComment(state.modelDeleteComment)
            is DeleteCommentActivityState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is DeleteCommentActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleSuccessDeleteComment(posts: ModelDeleteCommentResponseRemote) {
        if (viewModel.isCommentDeletedLoaded.not()) {
            viewModel.getPosts(sharedPrefs.getID())
            listComment.removeAt(position)
            setupComments(listComment)
        }
        viewModel.isCommentDeletedLoaded = true
        binding.swipeToRefresh.isRefreshing = false
    }

    //endregion

    //region addLike
    private fun handleStateChangeAddLike(state: AddLikeActivityState) {
        when (state) {
            is AddLikeActivityState.Init -> Unit
            is AddLikeActivityState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is AddLikeActivityState.Success -> handleSuccessAddLike(state.modelAddLike)
            is AddLikeActivityState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is AddLikeActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleSuccessAddLike(posts: ModelAddLikeResponseRemote) {
        if (viewModel.isLikedAddedLoaded.not()) {
            viewModel.getPosts(sharedPrefs.getID())
        }
        viewModel.isLikedAddedLoaded = true
        binding.swipeToRefresh.isRefreshing = false
    }
    //endregion

    //region deleteLike
    private fun handleStateChangeDeleteLike(state: DeleteLikeActivityState) {
        when (state) {
            is DeleteLikeActivityState.Init -> Unit
            is DeleteLikeActivityState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is DeleteLikeActivityState.Success -> handleSuccessDeleteLike(state.modelDeleteLike)
            is DeleteLikeActivityState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is DeleteLikeActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleSuccessDeleteLike(posts: ModelDeleteLikeResponseRemote) {
        if (viewModel.isLikedDeletedLoaded.not()) {
            viewModel.getPosts(sharedPrefs.getID())
        }
        viewModel.isLikedDeletedLoaded = true
        binding.swipeToRefresh.isRefreshing = false
    }
    //endregion

    private fun handleLoading(isLoading: Boolean) {
        binding.swipeToRefresh.isRefreshing = isLoading
    }

    private fun handleErrorLogin(errorCode: String, errorMessage: String) {
        requireActivity().showGenericAlertDialog(errorMessage)
        dismissLoading()
    }

    fun showBottomSheet() {
        bottomSheetCommentsDialog = BottomSheetDialog(requireContext())
        mbindingComments = LayoutCommentsBinding.inflate(layoutInflater)
        bottomSheetCommentsDialog.setContentView(mbindingComments.root)
        bottomSheetCommentsDialog.setCancelable(false)
        val parentLayout =
            bottomSheetCommentsDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        parentLayout?.let { it ->
            val behaviour = BottomSheetBehavior.from(it)
            setupFullHeight(it)
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }
        if (bottomSheetCommentsDialog.behavior.state == BottomSheetBehavior.STATE_DRAGGING) {
            bottomSheetCommentsDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        mbindingComments.imgBack.setOnClickListener {
            bottomSheetCommentsDialog.dismiss()
        }

        mbindingComments.imgSend.setOnClickListener {
            if (mbindingComments.edComment.text.toString().isBlank()) {
                mbindingComments.edComment.error = getString(R.string.add_comment)
            } else {
                viewModel.isCommentAddedLoaded = false
                val modelAddComment = ModelAddComment(
                    mbindingComments.edComment.text.toString(),
                    postID,
                    sharedPrefs.getID()
                )
                viewModel.addComment(modelAddComment)
                mbindingComments.edComment.setText("")
                mbindingComments.edComment.hint = getString(R.string.add_comment)
            }
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupComments(comments: List<Comment>?) {
        if (comments != null) {
            listComment = comments as ArrayList<Comment>
        }
        mbindingComments.rvComments.adapter = adapterComments
        adapterComments.notifyDataSetChanged()
        adapterComments.submitList(comments)


    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}