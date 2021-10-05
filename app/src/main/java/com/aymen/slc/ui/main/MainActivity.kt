package com.aymen.slc.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aymen.slc.R
import com.aymen.slc.databinding.ActivityMainBinding
import com.aymen.slc.databinding.NavHeaderBinding
import com.aymen.slc.global.helpers.DebugLog
import com.aymen.slc.global.utils.IS_CONNECTED_KEY
import com.aymen.slc.ui.base.BaseActivity
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    val viewModel: MainViewModel by viewModels()

    private val navFragment: Fragment
        get() = nav_host_fragment

     private val navHostFragment by lazy { navFragment as NavHostFragment }


    private val navGraph by lazy {
        navHostFragment
            .navController
            .navInflater
            .inflate(R.navigation.main_nav_graph)
    }

    private val navController by lazy { navHostFragment.navController }

    private lateinit var navView: NavigationView

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val headerBinding = NavHeaderBinding.bind(binding.navigationView.getHeaderView(0))

        bind(binding)
        bindHeaderView(headerBinding)

    }

    private fun bind(binding: ActivityMainBinding) {
        binding.lifecycleOwner = this
        setupNavGraph()
        binding.viewModel = viewModel
        registerMainObserver()
        setSupportActionBar(toolbar)
        initNavigation(binding)



    }

    private fun bindHeaderView(headerBinding: NavHeaderBinding) {
        headerBinding.viewModel = viewModel
        headerBinding.picasso = picasso
        headerBinding.lifecycleOwner = this

    }

    private fun initNavigation(binding: ActivityMainBinding) {
        appBarConfiguration = AppBarConfiguration(navGraph, binding.drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        navView = binding.navigationView
        navView.setupWithNavController(navController)

        setNavViewClickListeners()
        initNavControllerListener()


    }

    private fun setNavViewClickListeners() {
        navView
            .menu.findItem(R.id.logout)
            .setOnMenuItemClickListener {
                viewModel.showLogoutDialog(onLogout)
                true
            }
    }

    private val onLogout: () -> Unit = {
        viewModel.logout()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(navController, drawer_layout) || super.onSupportNavigateUp()
    }

    private fun initNavControllerListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.handleDestinationChange(destination.id)
        }
    }

    private fun setupNavGraph() {
        val navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)
        val isConnected = viewModel.isConnected
        navGraph.startDestination = if (isConnected) R.id.home else R.id.login
        navController.graph = navGraph
    }

    private fun registerMainObserver() {
        registerBaseObservers(viewModel)
        registerDrawerVisibility()
    }

    private fun registerDrawerVisibility() {
        viewModel.drawer.observe(this, Observer {
            if(it){
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }else{
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }
        })
    }
}