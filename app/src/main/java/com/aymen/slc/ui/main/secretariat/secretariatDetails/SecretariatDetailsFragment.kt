package com.aymen.slc.ui.main.secretariat.secretariatDetails

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aymen.slc.databinding.FragmentSecretariatDetailsBinding
import com.aymen.slc.global.helpers.DebugLog
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*



@AndroidEntryPoint
class SecretariatDetailsFragment : BaseFragment() {


    private val viewModel by viewModels<SecretariatDetailsViewModel>()

    private val args by navArgs<SecretariatDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding =
            FragmentSecretariatDetailsBinding.inflate(layoutInflater, container, false)

        bind(binding)

        return binding.root
    }


    private fun bind(binding: FragmentSecretariatDetailsBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel._conferee.postValue(args.conf)
        binding.viewModel = viewModel
        registerSecretariatDetailsObservers()

    }

    private fun registerSecretariatDetailsObservers() {
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
            is Navigation.ToLogin -> findNavController().navigate(SecretariatDetailsFragmentDirections.toLogin())
            else -> null
        }
    }
}