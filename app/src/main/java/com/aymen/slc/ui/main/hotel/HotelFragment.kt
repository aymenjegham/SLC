package com.aymen.slc.ui.main.hotel

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.aymen.slc.databinding.FragmentHotelBinding
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class HotelFragment : BaseFragment() {


    private val viewModel by viewModels<HotelViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding =
            FragmentHotelBinding.inflate(layoutInflater, container, false)

        bind(binding)

        return binding.root
    }

    private fun bind(binding: FragmentHotelBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        registerHotelObservers()

    }

    private fun registerHotelObservers() {
        registerBaseObservers(viewModel)
        registerLogoutObserver()
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
            is Navigation.ToLogin -> findNavController().navigate(HotelFragmentDirections.toLogin())
        }
    }
}