package com.aymen.slc.ui.base

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.aymen.slc.R
import com.aymen.slc.data.usecase.session.clear.ClearSessionUseCase
import com.aymen.slc.data.usecase.session.isConnected.IsUserConnectedUseCase
import com.aymen.slc.global.helpers.DebugLog
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.global.helpers.SingleEventLiveDataEvent
import com.aymen.slc.global.utils.NoInternetException
import com.aymen.slc.ui.data.DialogAction
import com.aymen.slc.ui.data.DialogData
import com.aymen.slc.ui.data.DialogType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection

abstract class BaseViewModel(
    private val app: Application,
    private val clearUseSessionUseCase: ClearSessionUseCase,
    isUserConnectedUseCase: IsUserConnectedUseCase
) : AndroidViewModel(app) {


    protected val context: Context
        get() = app.applicationContext

    private val _showProgressBar = SingleEventLiveDataEvent<DialogAction>()
    val showProgressBar: LiveData<DialogAction>
        get() = _showProgressBar

    private val _showStandardDialog = SingleEventLiveDataEvent<Pair<DialogAction, DialogData?>>()
    val showStandardDialog: LiveData<Pair<DialogAction, DialogData?>>
        get() = _showStandardDialog

    private val _hideKeyboard = SingleEventLiveDataEvent<Boolean>()
    val hideKeyboard: LiveData<Boolean>
        get() = _hideKeyboard

    private val _showToast: SingleEventLiveDataEvent<String> = SingleEventLiveDataEvent()
    val showToast: LiveData<String>
        get() = _showToast


    private val _showSnackBar = SingleEventLiveDataEvent<String>()
    val showSnackBar: LiveData<String>
        get() = _showSnackBar

    private val _navigation: SingleEventLiveDataEvent<Navigation> = SingleEventLiveDataEvent()
    val navigation: LiveData<Navigation>
        get() = _navigation

    protected var requestID: String = ""

    open val isUserConnected = isUserConnectedUseCase()
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    protected fun showStandardDialog(
        title: String? = null,
        message: String,
        ok: String? = null,
        cancel: String? = null,
        okAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        dialogType: DialogType,
        drawable: Drawable? = null,
        cancelable: Boolean = false
    ) {
        val data = DialogData.build(
            title,
            message,
            ok,
            cancel,
            okAction,
            cancelAction,
            dialogType,
            drawable
        )
        val action = if (cancelable) DialogAction.SHOW else DialogAction.SHOW_BLOCKING

        _showStandardDialog.value = Pair(action, data)
    }

    private fun showStandardDialog(
        @StringRes titleResId: Int? = null,
        @StringRes messageResId: Int,
        @StringRes okResId: Int? = null,
        @StringRes cancelResId: Int? = null,
        okAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        dialogType: DialogType,
        drawable: Drawable? = null,
        cancelable: Boolean = false
    ) {
        val message = app.getString(messageResId)
        val title = if (titleResId == null) null else app.getString(titleResId)
        val ok = if (okResId == null) app.getString(R.string.ok) else app.getString(okResId)
        val cancel =
            if (cancelResId == null) app.getString(R.string.cancel) else app.getString(cancelResId)

        showStandardDialog(
            title,
            message,
            ok,
            cancel,
            okAction,
            cancelAction,
            dialogType,
            drawable,
            cancelable
        )

    }

    protected fun showSimpleDialog(
        title: String? = null,
        message: String,
        ok: String? = null,
        cancel: String? = null,
        okAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        drawable: Drawable? = null,
        cancelable: Boolean = false
    ) = showStandardDialog(
        title,
        message,
        ok,
        cancel,
        okAction,
        cancelAction,
        DialogType.SIMPLE,
        drawable,
        cancelable
    )

    protected fun showSimpleDialog(
        @StringRes titleResId: Int? = null,
        @StringRes messageResId: Int,
        @StringRes okResId: Int? = null,
        okAction: (() -> Unit)? = null,
        cancelable: Boolean = false,
        drawable: Drawable? = null
    ) = showStandardDialog(
        titleResId = titleResId,
        messageResId = messageResId,
        okResId = okResId,
        okAction = okAction,
        dialogType = DialogType.SIMPLE,
        drawable = drawable,
        cancelable = cancelable
    )

    protected fun showChooseDialog(
        title: String? = null,
        message: String,
        ok: String? = null,
        cancel: String? = null,
        okAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        cancelable: Boolean = false,
        drawable: Drawable? = null
    ) = showStandardDialog(
        title,
        message,
        ok,
        cancel,
        okAction,
        cancelAction,
        DialogType.CHOOSE,
        drawable,
        cancelable
    )

    protected fun showChooseDialog(
        @StringRes titleResId: Int? = null,
        @StringRes messageResId: Int,
        @StringRes okResId: Int? = null,
        @StringRes cancelResId: Int? = null,
        okAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        drawable: Drawable? = null,
        cancelable: Boolean = false
    ) = showStandardDialog(
        titleResId,
        messageResId,
        okResId,
        cancelResId,
        okAction,
        cancelAction,
        DialogType.CHOOSE,
        drawable,
        cancelable
    )


    fun showToast(message: String) {
        _showToast.value = message
    }

    fun showSnackBar(msg: String) {
        _showSnackBar.value = msg
    }


    fun dismissDialog() {
        _showStandardDialog.value = Pair(DialogAction.DISMISS, null)
    }

    fun showProgressBar() {
        _showProgressBar.value = DialogAction.SHOW
    }

    fun showBlockingProgressBar() {
        _showProgressBar.value = DialogAction.SHOW_BLOCKING
    }

    fun hideProgressBar() {
        _showProgressBar.value = DialogAction.DISMISS
    }

    fun navigate(navigationTo: Navigation) {
        _navigation.value = navigationTo
    }

    open fun navigateBack(shouldFinish: Boolean) {
        _navigation.value = Navigation.Back(shouldFinish)
    }

    protected fun hideKeyboard() {
        _hideKeyboard.value = true
    }

    fun showNetworkError(
        okResId: Int? = null,
        okAction: (() -> Unit)? = null,
        retry: Boolean = false
    ) {
        if (retry) {
            showChooseDialog(
                null,
                R.string.unavailable_network_error,
                okResId = okResId ?: R.string.close,
                okAction = okAction,
                cancelAction = { navigate(Navigation.Back(true)) },
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_no_network)
            )
        } else {
            showSimpleDialog(
                null,
                R.string.unavailable_network_error,
                okResId = okResId ?: R.string.close,
                okAction = okAction,
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_no_network)
            )
        }

    }


    fun showServerError(
        okResId: Int? = null,
        okAction: (() -> Unit)? = null,
        retry: Boolean = false
    ) {
        if (retry) {
            showChooseDialog(
                R.string.server_error_title,
                R.string.server_error,
                okResId = okResId ?: R.string.close,
                okAction = okAction,
                cancelAction = { navigate(Navigation.Back(true)) },
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_no_network)

            )
        } else {
            showSimpleDialog(
                R.string.server_error_title,
                R.string.server_error,
                okResId = okResId ?: R.string.close,
                okAction = okAction,
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_no_network)

            )
        }

    }

    fun handleApiRequestFailureWithRefresh(
        action: () -> Unit,
        error: Throwable,
        handleRequestError: Boolean = false,
        retry: Boolean = false
    ) {

        DebugLog.e("SLC", "API Error", error)

        val failure: (Throwable) -> Unit = {
            if (retry) handleApiRequestFailureRetry(it, action, handleRequestError)
            else handleApiRequestFailure(it, handleRequestError)
        }

        if (error is HttpException && error.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            viewModelScope.launch {
                runCatching {
                    withContext(Dispatchers.IO) {
                        /*refreshSessionUseCase()*/
                        clearUseSessionUseCase()
                    }
                }.onSuccess {
                    action()
                }.onFailure(failure)
            }
        } else {
            failure(error)
        }
    }

    fun handleApiRequestFailure(
        error: Throwable,
        handleResponseFail: Boolean
    ) {
        DebugLog.e("SLC", "API Error", error)
        hideProgressBar()
        when (error) {
            is NoInternetException -> showNetworkError()
            is HttpException -> showAPIErrorDialog(
                requestID,
                error,
                handleRequestError = handleResponseFail
            )
            else -> showServerError()
        }
    }

    private fun handleApiRequestFailureRetry(
        error: Throwable,
        retryAction: () -> Unit,
        handleResponseFail: Boolean
    ) {
        hideProgressBar()
        when (error) {
            is NoInternetException -> showNetworkError(R.string.retry, retryAction, true)
            is HttpException -> showAPIErrorDialog(
                requestID,
                error,
                R.string.retry,
                retryAction,
                handleResponseFail,
                true
            )
            else -> showServerError(R.string.retry, retryAction, true)
        }

    }

    protected open fun showAPIErrorDialog(
        requestID: String,
        error: HttpException,
        okResId: Int? = null,
        action: (() -> Unit)? = null,
        handleRequestError: Boolean = false,
        retry: Boolean = false
    ) {

        when {
            error.code() == HttpsURLConnection.HTTP_UNAUTHORIZED -> showSessionExpiredDialog()


            handleRequestError ->

                error.response()?.errorBody().apply {
                    if (retry) {
                        showChooseDialog(
                            R.string.server_error_title,
                            R.string.server_error,
                            okAction = action,
                            drawable = ContextCompat.getDrawable(context, R.drawable.ic_no_network),
                            cancelAction = { navigate(Navigation.Back(true)) })
                    } else {
                        showSimpleDialog(
                            R.string.server_error_title,
                            R.string.server_error,
                            drawable = ContextCompat.getDrawable(context, R.drawable.ic_no_network),
                            okAction = action
                        )
                    }
                }

            else -> showServerError(okResId, action, retry)
        }
    }

    protected fun <T> runActionWithProgress(
        action: suspend () -> T,
        success: suspend  (T) -> Unit,
        failure: (Throwable) -> Unit = { handleApiRequestFailure(it, true) }
    ) {
        actionWithProgress(action, success, failure)()
    }

    protected fun <T> actionWithProgress(
        action: suspend () -> T,
        success: suspend  (T) -> Unit,
        failure: (Throwable) -> Unit = { handleApiRequestFailure(it, true) }
    ): () -> Unit = {
        viewModelScope.launch {
            showBlockingProgressBar()
            runCatching {
                withContext(Dispatchers.IO) {
                    action()
                }
            }.onSuccess {
                hideProgressBar()
                success.invoke(it)
            }.onFailure(failure)
        }
    }

    fun showSessionExpiredDialog() {

        showSimpleDialog(
            R.string.session_expired,
            R.string.session_expired_statement,
            okAction = {
                runBlocking {
                    // clearUseSessionUseCase()
                }
                navigate(Navigation.Login)
            },
            cancelable = false
        )
    }

    fun <T> runActionWithConfirmation(
        title: String,
        message: String,
        action: suspend () -> T,
        success: suspend  (T) -> Unit,
        failure: (Throwable) -> Unit = { handleApiRequestFailure(it, true) }
    ) {

        showChooseDialog(
            title,
            message,
            context.getString(R.string.confirm),
            okAction = { runActionWithProgress(action, success, failure) }
        )

    }


}