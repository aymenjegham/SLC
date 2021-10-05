package com.aymen.slc.ui.main.secretariat.secretariatDetails

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aymen.slc.data.model.Conferee
import com.aymen.slc.data.usecase.session.clear.ClearSessionUseCase
import com.aymen.slc.data.usecase.session.isConnected.IsUserConnectedUseCase
import com.aymen.slc.ui.base.BaseViewModel


class SecretariatDetailsViewModel @ViewModelInject constructor(
    app: Application,
    clearSessionUseCase: ClearSessionUseCase,
    isUserConnectedUseCase: IsUserConnectedUseCase
) : BaseViewModel
    (
    app,
    clearSessionUseCase,
    isUserConnectedUseCase
) {


    val _conferee = MutableLiveData<Conferee>()
    val conferee: LiveData<Conferee>
        get() = _conferee


    override val isUserConnected: LiveData<Boolean>
        get() = super.isUserConnected

}