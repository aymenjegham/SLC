package com.aymen.slc.ui.main.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aymen.slc.databinding.FragmentLoginBinding
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding =
            FragmentLoginBinding.inflate(layoutInflater, container, false)

        bind(binding)

        return binding.root
    }

    private fun bind(binding: FragmentLoginBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        registerLoggingObservers()
    }

    private fun registerLoggingObservers() {
        registerBaseObservers(viewModel)
    }

    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Home -> findNavController().navigate(LoginFragmentDirections.toHome())
        }
    }
}