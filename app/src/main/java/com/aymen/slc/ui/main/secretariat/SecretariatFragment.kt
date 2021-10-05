package com.aymen.slc.ui.main.secretariat

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.aymen.slc.R
import com.aymen.slc.databinding.FragmentSecretariatBinding
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.ui.base.BaseFragment
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_secretariat.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class SecretariatFragment : BaseFragment() {

    private lateinit var codeScanner: CodeScanner

    private val viewModel by viewModels<SecretariatViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding =
            FragmentSecretariatBinding.inflate(layoutInflater, container, false)

        bind(binding)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        codeScanner = CodeScanner(requireActivity(), scanner_view)
        codeScanner.decodeCallback = viewModel.decodeCallback()

        launch_button.setOnClickListener {
            codeScanner.startPreview()
        }
    }


    private fun bind(binding: FragmentSecretariatBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        registerSecretariatObservers()

    }

    private fun registerSecretariatObservers() {
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

            is Navigation.ToLogin -> findNavController().navigate(SecretariatFragmentDirections.toLogin())

            is Navigation.ToSecretariatDetails -> findNavController().navigate(SecretariatFragmentDirections.toSecretariatDetails(navigationTo.conferee))

            else -> null
        }
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}