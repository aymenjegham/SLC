package com.aymen.slc.ui.base


import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.aymen.slc.global.helpers.Navigation
import com.squareup.picasso.Picasso
import dagger.Lazy
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment : Fragment() {



    @Inject
    protected lateinit var picassoLazy: Lazy<Picasso>

    protected val picasso: Picasso
        get() = picassoLazy.get()


    protected fun registerBaseObservers(viewModel: BaseViewModel) {
        registerStandardDialogObserver(viewModel)
        registerProgressDialogObserver(viewModel)
        registerHideKeyboardObserver(viewModel)
        registerShowToastObserver(viewModel)
        registerSnackBarObserver(viewModel)
        registerNavigationObserver(viewModel)
    }


    private fun registerStandardDialogObserver(viewModel: BaseViewModel) {
        (activity as? BaseActivity)?.apply {
            viewModel.showStandardDialog.observe(
                viewLifecycleOwner,
                Observer(::showStandardDialog)
            )
        }
    }

    private fun registerProgressDialogObserver(viewModel: BaseViewModel) {
        (activity as? BaseActivity)?.apply {
            viewModel.showProgressBar.observe(viewLifecycleOwner, Observer(::showProgressDialog))
        }
    }

    private fun registerHideKeyboardObserver(viewModel: BaseViewModel) {
        (activity as? BaseActivity)?.apply {
            viewModel.hideKeyboard.observe(viewLifecycleOwner, Observer { hideKeyboard() })
        }
    }

    private fun registerShowToastObserver(viewModel: BaseViewModel) {
        (activity as? BaseActivity)?.apply {
            viewModel.showToast.observe(viewLifecycleOwner, Observer(::showToast))
        }

    }

    private fun registerSnackBarObserver(viewModel: BaseViewModel) {
        (activity as? BaseActivity)?.apply {
            viewModel.showSnackBar.observe(viewLifecycleOwner, Observer(::showSnackBar))
        }
    }

    private fun registerNavigationObserver(viewModel: BaseViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, Observer (::navigate))
    }

    protected open fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> findNavController().navigateUp()
            else -> (activity as? BaseActivity)?.navigate(navigationTo)

        }
    }

    fun navigateToActivity(kClass: KClass<out Activity>, shouldFinish: Boolean = false) {
        (activity as? BaseActivity)?.navigateToActivity(kClass, shouldFinish)
    }

    fun navigateToActivity(intent: Intent, shouldFinish: Boolean = false) {
        (activity as? BaseActivity)?.navigateToActivity(intent, shouldFinish)
    }

}
