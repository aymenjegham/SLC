package com.aymen.slc.ui.main.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.aymen.slc.data.model.permission.PermissionType
import com.aymen.slc.databinding.FragmentHomeBinding
import com.aymen.slc.global.helpers.DebugLog
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.ui.base.BaseFragment
import com.aymen.slc.ui.main.home.adapter.RoleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment : BaseFragment() {


    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var roleAdapter: RoleAdapter

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding =
            FragmentHomeBinding.inflate(layoutInflater, container, false)

        bind(binding)

        registerHomeObservers()

        return binding.root
    }


    private fun bind(binding: FragmentHomeBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        roleAdapter = RoleAdapter(
            viewModel.navigate(),
            requireContext()
        )
        binding.adapter = roleAdapter


    }


    private fun registerHomeObservers() {
        registerBaseObservers(viewModel)
        registerLogoutObserver()
        registerSubmitAdapter()
        initPermissionLauncher(viewModel)
        registerRequestPermissionObserver(viewModel)
        registerCheckPermissionObserver(viewModel)

    }

    private fun registerCheckPermissionObserver(viewModel: HomeViewModel) {
        viewModel.checkPermission.observe(viewLifecycleOwner, Observer(::checkPermission))

    }

    private fun registerRequestPermissionObserver(viewModel: HomeViewModel) {
        viewModel.requestPermission.observe(viewLifecycleOwner, Observer {
            requestPermissionLauncher.launch(it.permission)
        })
    }

    private fun initPermissionLauncher(viewModel: HomeViewModel) {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                viewModel.handlePermissionState(it, false)
            }
    }

    private fun registerSubmitAdapter() {
        viewModel.roles.observe(viewLifecycleOwner, Observer {
            it?.let {
                roleAdapter.submitList(it)
            }
        })
    }

    private fun checkPermission(isRequested: Boolean) {
        val isGranted = when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                    || ContextCompat.checkSelfPermission(
                requireContext(), getPermission()
            ) == PackageManager.PERMISSION_GRANTED -> {
                true
            }

            (shouldShowRequestPermissionRationale(getPermission()) && isRequested)
                    || !isRequested -> {
                false
            }

            else -> null
        }

        viewModel.handlePermissionState(isGranted, true)
    }

    private fun registerLogoutObserver() {
        viewModel.isUserConnected.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                navigate(Navigation.ToLogin)
                activity?.drawer_layout?.closeDrawer(Gravity.LEFT)
            }
        })
    }

    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {


            is Navigation.ToLogin -> findNavController().navigate(HomeFragmentDirections.toLogin())

            is Navigation.ToSuperAdmin -> findNavController().navigate(HomeFragmentDirections.toSuperAdmin())

            is Navigation.ToSecretariat -> findNavController().navigate(HomeFragmentDirections.toSecretariat())

            is Navigation.ToWorkshop -> findNavController().navigate(HomeFragmentDirections.toWorkshop())

            is Navigation.ToHotel -> findNavController().navigate(HomeFragmentDirections.toHotel())

            is Navigation.ToEvent -> findNavController().navigate(HomeFragmentDirections.toEvent())

            is Navigation.ToRestaurant -> findNavController().navigate(HomeFragmentDirections.toRestaurant())

            is Navigation.ToConference -> findNavController().navigate(HomeFragmentDirections.toConference())

            else -> super.navigate(navigationTo)
        }
    }

    private fun getPermission() =
        when (viewModel.permissionType) {
            PermissionType.ACCESS_DATA -> Manifest.permission.WRITE_EXTERNAL_STORAGE
            PermissionType.LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
            else -> Manifest.permission.CAMERA
        }

    private val PermissionType.permission
        get() = when (this) {
            PermissionType.CAMERA -> Manifest.permission.CAMERA
            PermissionType.LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
            else -> Manifest.permission.WRITE_EXTERNAL_STORAGE
        }

}