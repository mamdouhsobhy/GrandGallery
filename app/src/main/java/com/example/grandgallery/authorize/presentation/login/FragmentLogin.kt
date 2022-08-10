package com.example.grandgallery.authorize.presentation.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.grand.authorize.data.responseremote.ModelLoginResponseRemote
import com.example.grand.authorize.domain.requestremote.ModelLogin
import com.example.grandgallery.homedonor.ActivityHomeDonor
import com.example.grandgallery.R
import com.example.grandgallery.authorize.presentation.login.viewmodel.LoginActivityState
import com.example.grandgallery.authorize.presentation.login.viewmodel.LoginViewModel
import com.example.grandgallery.core.presentation.base.BaseFragmentBinding
import com.example.grandgallery.core.presentation.common.SharedPrefs
import com.example.grandgallery.core.presentation.extensions.showGenericAlertDialog
import com.example.grandgallery.core.presentation.extensions.showToast
import com.example.grandgallery.core.presentation.utilities.isEmpty
import com.example.grandgallery.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class FragmentLogin : BaseFragmentBinding<FragmentLoginBinding>() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private val viewModel: LoginViewModel by viewModels()
    lateinit var modelLogin: ModelLogin
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListenerOnView()
        observeStateFlow()

    }

    private fun addListenerOnView() {


        binding.btnLogin.setOnClickListener {

            if(validateForm()){
                viewModel.isScreenLoaded=false
                modelLogin= ModelLogin(binding.editPhone.text.toString(),binding.editPassword.text.toString())
                if(viewModel.isScreenLoaded.not()) {
                    viewModel.makeLogin(modelLogin)
                }

            }

        }


    }

    private fun validateForm(): Boolean {
        return if (binding.editPhone.isEmpty(getString(R.string.phone))) {
            false
        } else !(binding.editPassword.isEmpty(getString(R.string.password)))

    }


    private fun observeStateFlow() {
        viewModel.loginState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleStateChange(state: LoginActivityState) {
        when (state) {
            is LoginActivityState.Init -> Unit
            is LoginActivityState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is LoginActivityState.Success -> handleSuccess(state.modelLogin)
            is LoginActivityState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )
            is LoginActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading){
            showLoading()
        }else{
            dismissLoading()
        }
    }

    private fun handleSuccess(modelLogin: ModelLoginResponseRemote) {

        sharedPrefs.saveToken("${modelLogin.token}")
        sharedPrefs.saveID("${modelLogin.id}")
        sharedPrefs.saveUserRole("${modelLogin.roleName}")
        sharedPrefs.saveName("${modelLogin.userName}")
        viewModel.isScreenLoaded=true
        intentToHome(modelLogin.roleName)
    }
    private fun handleErrorLogin(errorCode: String, errorMessage: String) {

        requireActivity().showGenericAlertDialog(errorMessage)
        dismissLoading()



    }
    @SuppressLint("CheckResult")
    private fun intentToHome(roleName: String?) {
        when (roleName) {
            "Admin" -> {
                    sharedPrefs.saveFound("")
                    val intent = Intent(context, ActivityHomeDonor::class.java)
                    startActivity(intent)
                    requireActivity().finish()
            }else -> {

                    sharedPrefs.saveFound("NotFound")
                    dismissLoading()
                    Toast.makeText(requireActivity(),getString(R.string.no_account_found),Toast.LENGTH_SHORT).show()

                }
            }
        }

    }

