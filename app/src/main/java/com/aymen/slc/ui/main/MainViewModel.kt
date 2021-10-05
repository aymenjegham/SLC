package com.aymen.slc.ui.main

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aymen.slc.R
import com.aymen.slc.data.usecase.session.clear.ClearSessionUseCase
import com.aymen.slc.data.usecase.session.isConnected.IsUserConnectedUseCase
import com.aymen.slc.data.usecase.user.getUser.GetUserUseCase
import com.aymen.slc.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainViewModel @ViewModelInject constructor(
    app: Application,
    getUserUseCase: GetUserUseCase,
    private val clearSessionUseCase: ClearSessionUseCase,
    private val isUserConnectedUseCase: IsUserConnectedUseCase
) : BaseViewModel(app, clearSessionUseCase, isUserConnectedUseCase) {

    private val _drawer = MutableLiveData<Boolean>()
    val drawer: LiveData<Boolean>
        get() = _drawer

     val user = getUserUseCase()
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    val isConnected = runBlocking(Dispatchers.IO) { isUserConnectedUseCase().first() }


    fun showLogoutDialog(action: () -> Unit) {
        showChooseDialog(
            titleResId = R.string.attention,
            messageResId = R.string.do_you_really_want_to_logout,
            okResId = R.string.confirm,
            cancelResId = R.string.cancel,
            okAction = action,
            cancelable = true
        )
    }

    fun logout() {
        viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            clearSessionUseCase()
        }
    }

    fun handleDestinationChange(destinationId: Int) {
        when (destinationId) {
            R.id.login -> {
                _drawer.postValue(true)
            }

            R.id.home -> {
                _drawer.postValue(false)
            }

            R.id.superAdmin -> {
                _drawer.postValue(false)
            }

            R.id.secretariat -> {
                _drawer.postValue(false)
            }

            R.id.hotel -> {
                _drawer.postValue(false)
            }

            R.id.event -> {
                _drawer.postValue(false)
            }

            R.id.restaurant -> {
                _drawer.postValue(false)
            }

            R.id.conference -> {
                _drawer.postValue(false)
            }

            R.id.secretariatDetails -> {
                _drawer.postValue(false)
            }
        }
    }
}