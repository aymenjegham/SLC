package com.aymen.slc.ui.main.restaurant

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.aymen.slc.databinding.FragmentRestaurantBinding
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.ui.base.BaseFragment
import com.budiyev.android.codescanner.CodeScanner
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_secretariat.*
import android.os.VibrationEffect

import android.os.Build

import androidx.core.content.ContextCompat.getSystemService

import android.os.Vibrator
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService


@AndroidEntryPoint
class RestaurantFragment : BaseFragment() {

    private lateinit var codeScanner: CodeScanner

    private val viewModel by viewModels<RestaurantViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding =
            FragmentRestaurantBinding.inflate(layoutInflater, container, false)

        bind(binding)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        codeScanner = CodeScanner(requireActivity(), scanner_view)
        codeScanner.decodeCallback = viewModel.decodeCallback()

        launch_button.setOnClickListener {
            codeScanner.startPreview()
            viewModel._success.postValue(false)
        }
    }

    private fun bind(binding: FragmentRestaurantBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        registerRestaurantObservers()

    }

    private fun registerRestaurantObservers() {
        registerBaseObservers(viewModel)
        registerLogoutObserver()
        registerVibrationObserver()
    }

    private fun registerVibrationObserver() {
        viewModel.vibrate.observe(viewLifecycleOwner, Observer {
            vibrateDevice(requireContext(),700L)
        })
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
            is Navigation.ToLogin -> findNavController().navigate(RestaurantFragmentDirections.toLogin())
        }
    }


    private fun vibrateDevice(context: Context, duration: Long) {
        val vibrator = getSystemService(context, Vibrator::class.java)
        vibrator?.let {
            if (Build.VERSION.SDK_INT >= 26) {
                it.vibrate(
                    VibrationEffect.createOneShot(
                        duration,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                @Suppress("DEPRECATION")
                it.vibrate(duration)
            }
        }
    }
}