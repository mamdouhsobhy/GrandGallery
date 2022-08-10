package com.example.grandgallery.sidemenu.addpost

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.grandgallery.R
import com.example.grandgallery.authorize.data.repositoryimb.LoginRepositoryImpl_Factory.create
import com.example.grandgallery.core.presentation.base.BaseFragmentBinding
import com.example.grandgallery.core.presentation.extensions.loadImage
import com.example.grandgallery.core.presentation.extensions.showGenericAlertDialog
import com.example.grandgallery.core.presentation.extensions.showToast
import com.example.grandgallery.databinding.FragmentAddPostBinding
import com.example.grandgallery.homedonor.data.responseremote.addpost.ModelAddPostResponseRempte
import com.example.grandgallery.sidemenu.addpost.viewmodel.AddPostActivityState
import com.example.grandgallery.sidemenu.addpost.viewmodel.AddPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import droidninja.filepicker.utils.ContentUriUtils
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class FragmentAddPost : BaseFragmentBinding<FragmentAddPostBinding>() {

    val viewModel: AddPostViewModel by lazy {
        ViewModelProvider(requireActivity())[AddPostViewModel::class.java]
    }
    val map = HashMap<String, RequestBody>()
    var postAttach: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListenerOnView()
        observeStateFlow()
    }

    private fun addListenerOnView() {
        binding.imgPost.setOnClickListener {
            intentToGallery()
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.isScreenLoaded=false
            val c: Date = Calendar.getInstance().getTime()
            println("Current time => $c")

            val df = SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.getDefault())
            val formattedDate: String = df.format(c)
            map["Date"] = createPartFromString(formattedDate)
            map["Content"] =createPartFromString(binding.edNotes.text.toString())


            if(viewModel.isScreenLoaded.not()) {
                ContentUriUtils.getFilePath(requireActivity(), postAttach!!)
                    ?.let { it1 -> prepareFilePart("Image", it1) }?.let { it2 ->
                        viewModel.getPosts(map,
                            it2
                        )
                    }

              }
        }
    }

    private fun observeStateFlow() {
        viewModel.addPostsState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun handleStateChange(state: AddPostActivityState) {
        when (state) {
            is AddPostActivityState.Init -> Unit
            is AddPostActivityState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is AddPostActivityState.Success -> handleSuccess(state.modelGetPosts)
            is AddPostActivityState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is AddPostActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleSuccess(posts: ModelAddPostResponseRempte) {
        Toast.makeText(requireContext(),"success",Toast.LENGTH_SHORT).show()
        viewModel.isScreenLoaded = true
        dismissLoading()
        findNavController().navigate(R.id.homeDonorFragment)
    }


    private fun handleLoading(isLoading: Boolean) {
        if(isLoading){
            showLoading()
        }else{
            dismissLoading()
        }
    }

    private fun handleErrorLogin(errorCode: String, errorMessage: String) {
        requireActivity().showGenericAlertDialog(errorMessage)
        dismissLoading()
    }

    private fun intentToGallery() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                100
            )
            return
        }
        FilePickerBuilder.instance
            .setMaxCount(1) //optional
            .enableVideoPicker(false)
            .enableDocSupport(false)
            .enableImagePicker(true)
            .enableSelectAll(false)
            .pickPhoto(this, 10)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {

            when (requestCode) {
                10 -> {
                     postAttach =
                        data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)
                            ?.first()
                    binding.imgPost.loadImage(requireContext(),postAttach.toString())

                }

            }
        }

    }

    fun createPartFromString(description: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, description)
    }

    private fun prepareFilePart(partName: String, fileUri: String): MultipartBody.Part {
        val file = File(fileUri)
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }
}