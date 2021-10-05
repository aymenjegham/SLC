package com.aymen.slc.ui.main.workshop

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.aymen.slc.databinding.FragmentWorkshopBinding
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class WorkshopFragment : BaseFragment() {


    private val viewModel by viewModels<WorkshopViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding =
            FragmentWorkshopBinding.inflate(layoutInflater, container, false)

        bind(binding)

        return binding.root
    }

    private fun bind(binding: FragmentWorkshopBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        registerWorkshopObservers()

    }

    private fun registerWorkshopObservers() {
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
            is Navigation.ToLogin -> findNavController().navigate(WorkshopFragmentDirections.toLogin())
        }
    }
}