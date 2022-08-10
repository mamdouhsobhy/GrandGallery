package com.example.grandgallery.homedonor

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.grandgallery.R
import com.example.grandgallery.core.presentation.base.BaseActivity
import com.example.grandgallery.core.presentation.common.SharedPrefs
import com.example.grandgallery.core.presentation.utilities.Nav
import com.example.grandgallery.databinding.ActivityHomeDonorBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivityHomeDonor : BaseActivity<ActivityHomeDonorBinding>() {
    private lateinit var navController: NavController
    @Inject
    lateinit var sharedPrefs: SharedPrefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListenerOnView()
        initialize()
        initiateBottomNavigation()
        binding.drawer.txtName.text=sharedPrefs.getName()

    }

    private fun addListenerOnView() {
        binding.imgMenu.setOnClickListener {

            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        binding.drawer.txtInfoNourRahman.setOnClickListener {
            closeDrawer()
            navController.navigate(R.id.fragmentCharityInfo)
        }
        binding.drawer.txtInfoTamra.setOnClickListener {
            closeDrawer()
            navController.navigate(R.id.fragmentTamraInfo)
        }
        binding.drawer.txtActiveTamra.setOnClickListener {
            closeDrawer()
            navController.navigate(R.id.fragmentAddPost)
        }
        binding.drawer.txtContactUs.setOnClickListener {
            closeDrawer()
            navController.navigate(R.id.fragmentSelectType)
        }

    }


    private fun initiateBottomNavigation() {
        binding.bottomNavigationView.itemIconTintList = null

        binding.bottomNavigationView.setupWithNavController(navController)
    }


    private fun initialize() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.homeDonorFragment -> {

                }

            }
        }
    }

    private fun closeDrawer(){
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }
}