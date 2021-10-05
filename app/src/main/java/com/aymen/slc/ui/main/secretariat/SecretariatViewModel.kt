package com.aymen.slc.ui.main.secretariat

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aymen.slc.data.model.Conferee
import com.aymen.slc.data.usecase.conferee.getConfereeById.GetConfereeByIdUseCase
import com.aymen.slc.data.usecase.session.clear.ClearSessionUseCase
import com.aymen.slc.data.usecase.session.isConnected.IsUserConnectedUseCase
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.ui.base.BaseViewModel
import com.budiyev.android.codescanner.DecodeCallback

class SecretariatViewModel @ViewModelInject constructor(
    app: Application,
    clearSessionUseCase: ClearSessionUseCase,
    isUserConnectedUseCase: IsUserConnectedUseCase,
    private val getConfereeByIdUseCase: GetConfereeByIdUseCase
) : BaseViewModel
    (
    app,
    clearSessionUseCase,
    isUserConnectedUseCase
) {


    override val isUserConnected: LiveData<Boolean>
        get() = super.isUserConnected


    fun decodeCallback() = DecodeCallback {

        it.text?.let { id ->
            getConferee(id)
        }

    }

    private fun getConferee(id: String) {

         val  onSuccess : suspend (Conferee) -> Unit =  {
             navigate(Navigation.ToSecretariatDetails(it))
         }

        val action: suspend () -> Conferee = {
            getConfereeByIdUseCase(id)
        }

        runActionWithProgress(action, onSuccess)
    }
}