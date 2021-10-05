package com.aymen.slc.ui.main.workshop

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aymen.slc.data.usecase.session.clear.ClearSessionUseCase
import com.aymen.slc.data.usecase.session.isConnected.IsUserConnectedUseCase
import com.aymen.slc.ui.base.BaseViewModel

class WorkshopViewModel @ViewModelInject constructor(
    app: Application,
    clearSessionUseCase: ClearSessionUseCase,
    isUserConnectedUseCase: IsUserConnectedUseCase
) : BaseViewModel
    (
    app,
    clearSessionUseCase,
    isUserConnectedUseCase
) {


    override val isUserConnected: LiveData<Boolean>
        get() = super.isUserConnected



}