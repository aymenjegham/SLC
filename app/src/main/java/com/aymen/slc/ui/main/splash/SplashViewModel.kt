package com.aymen.slc.ui.main.splash

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.aymen.slc.data.usecase.session.clear.ClearSessionUseCase
import com.aymen.slc.data.usecase.session.isConnected.IsUserConnectedUseCase
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel @ViewModelInject constructor(
    app: Application,
    private val isUserConnectedUseCase: IsUserConnectedUseCase,
    private val clearSessionUseCase: ClearSessionUseCase
) : BaseViewModel(app, clearSessionUseCase, isUserConnectedUseCase) {


    init {
        viewModelScope.launch {
            delay(3000)
            val isConnected = withContext(Dispatchers.IO) { isUserConnectedUseCase().first() }
            navigate(Navigation.Home(isConnected))
        }
    }

}