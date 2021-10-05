package com.aymen.slc.ui.main.login

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aymen.slc.R
import com.aymen.slc.data.usecase.session.clear.ClearSessionUseCase
import com.aymen.slc.data.usecase.session.isConnected.IsUserConnectedUseCase
import com.aymen.slc.data.usecase.user.login.LoginUserUseCase
import com.aymen.slc.global.extension.isEmailValid
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.ui.base.BaseViewModel
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection


const val EMAIL_ERROR_KEY = 1
const val PASSWORD_ERROR_KEY = 2

class LoginViewModel @ViewModelInject constructor(
    app: Application,
    private val loginUserUseCase: LoginUserUseCase,
    private val clearSessionUseCase: ClearSessionUseCase,
    private val isUserConnectedUseCase: IsUserConnectedUseCase
) : BaseViewModel(app, clearSessionUseCase,isUserConnectedUseCase) {

    val password = MutableLiveData<String>()

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?>
        get() = _passwordError


    val email = MutableLiveData<String?>()

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?>
        get() = _emailError

    fun login() {

        if (isDataIsValid()) {
            val onSuccess: suspend (Unit) -> Unit = {
                navigate(Navigation.Home(false))
            }

            val action: suspend () -> Unit = {
                loginUserUseCase(email.value!!, password.value!!)
            }

            runActionWithProgress(action, onSuccess)
        }

    }

    private fun isDataIsValid(): Boolean {

        var dataIsValid = true

        if (email.value?.isEmailValid() != true) {
            _emailError.value =
                context.getString(R.string.email_non_valid_error)

            dataIsValid = false
        }

        if (password.value?.length ?: 0 < 4) {
            _passwordError.value =
                context.getString(R.string.password_length_error)

            dataIsValid = false
        }

        return dataIsValid
    }


    fun clearError(filedErrorKey: Int) {
        when (filedErrorKey) {
            EMAIL_ERROR_KEY -> _emailError
            PASSWORD_ERROR_KEY -> _passwordError
            else -> null
        }?.value = null
    }


    override fun showAPIErrorDialog(
        requestID: String,
        error: HttpException,
        okResId: Int?,
        action: (() -> Unit)?,
        handleRequestError: Boolean,
        retry: Boolean
    ) {
        val action: () -> Unit = { dismissDialog() }
        if (error.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            showSimpleDialog(
                R.string.connection_failed,
                R.string.incorrect_username_or_password,
                okAction = action
            )
        } else {
            super.showAPIErrorDialog(requestID, error, okResId, action, handleRequestError, retry)
        }

    }
}