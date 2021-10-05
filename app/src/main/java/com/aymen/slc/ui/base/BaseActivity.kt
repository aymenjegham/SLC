package com.aymen.slc.ui.base

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aymen.slc.global.helpers.DebugLog
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.ui.data.DialogAction
import com.aymen.slc.ui.data.DialogData
import com.aymen.slc.ui.dialog.progress.ProgressDialog
import com.aymen.slc.ui.dialog.standard.StandardDialog
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.Lazy
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    private var standardDialog: StandardDialog? = null

    @Inject
    protected lateinit var picassoLazy: Lazy<Picasso>

    protected val picasso: Picasso
        get() = picassoLazy.get()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
    }


    protected fun registerBaseObservers(viewModel: BaseViewModel) {
        registerStandardDialogObserver(viewModel)
        registerProgressDialogObserver(viewModel)
        registerHideKeyboardObserver(viewModel)
        registerShowToastObserver(viewModel)
        registerSnackBarObserver(viewModel)
        registerNavigationObserver(viewModel)

    }

    private fun registerProgressDialogObserver(viewModel: BaseViewModel) {
        viewModel.showProgressBar.observe(this, Observer(::showProgressDialog))
    }

    private fun registerStandardDialogObserver(viewModel: BaseViewModel) {
        viewModel.showStandardDialog.observe(this, Observer(::showStandardDialog))
    }

    private fun registerHideKeyboardObserver(viewModel: BaseViewModel) {
        viewModel.hideKeyboard.observe(this, Observer { hideKeyboard() })
    }

    private fun registerShowToastObserver(viewModel: BaseViewModel) {
        viewModel.showToast.observe(this, Observer(::showToast))
    }

    private fun registerSnackBarObserver(viewModel: BaseViewModel) {
        viewModel.showSnackBar.observe(this, Observer(::showSnackBar))
    }

    private fun registerNavigationObserver(viewModel: BaseViewModel) {
        viewModel.navigation.observe(this, Observer(::navigate))
    }

    fun showProgressDialog(action: DialogAction) {
        progressDialog = if (action == DialogAction.DISMISS) {
            progressDialog?.dismiss()
            null
        } else {
            ProgressDialog(this, action == DialogAction.SHOW)
                .apply { show() }
        }
    }

    fun showStandardDialog(settings: Pair<DialogAction, DialogData?>) {
        val (action, data) = settings

        standardDialog = if (action == DialogAction.DISMISS) {
            standardDialog?.dismiss()
            null
        } else {

            StandardDialog(
                this,
                data!!,
                action == DialogAction.SHOW
            ).apply { show() }
        }

    }

    fun hideKeyboard() {
        try {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        } catch (e: Exception) {
            DebugLog.d(
                "KeyBoardError",
                "Could not hide keyboard, window unreachable. " + e.toString()
            )
        }
    }

    fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showSnackBar(message: String) {
        Snackbar.make(window.decorView.rootView, message, Snackbar.LENGTH_INDEFINITE).show()
    }

    fun navigateToActivity(kClass: KClass<out Activity>, shouldFinish: Boolean = false) {
        startActivity(Intent(this, kClass.java))
        if (shouldFinish) finish()
    }

    fun navigateToActivity(intent: Intent, shouldFinish: Boolean = false) {
        startActivity(intent)
        if (shouldFinish) finish()

    }

    open fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> navigateBack(navigationTo.ShouldFinish)
            is Navigation.Settings -> navigateToSettings()
        }

    }

    private fun navigateBack(shouldFinish: Boolean) {
        if (shouldFinish) {
            super.onBackPressed()
            finish()
        } else {
            onBackPressed()
        }
    }

    private fun navigateToSettings() {
        Intent()
            .apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", packageName, null)
            }.also {
                startActivity(it)
            }
    }


}