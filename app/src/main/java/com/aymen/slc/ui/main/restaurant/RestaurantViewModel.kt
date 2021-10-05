package com.aymen.slc.ui.main.restaurant

import android.app.Application
import android.content.Context
import android.os.Vibrator
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aymen.slc.data.model.Conferee
import com.aymen.slc.data.usecase.conferee.addConferee.AddConfereeUseCase
import com.aymen.slc.data.usecase.conferee.checkRestaurantAttendantExists.CheckRestaurantAttendantExistsUseCase
import com.aymen.slc.data.usecase.conferee.getConfereeById.GetConfereeByIdUseCase
import com.aymen.slc.data.usecase.conferee.getRestaurantAttendants.GetRestaurantAttendantsCountUseCase
import com.aymen.slc.data.usecase.session.clear.ClearSessionUseCase
import com.aymen.slc.data.usecase.session.isConnected.IsUserConnectedUseCase
import com.aymen.slc.global.helpers.DebugLog
import com.aymen.slc.global.helpers.SingleEventLiveDataEvent
import com.aymen.slc.ui.base.BaseViewModel
import com.budiyev.android.codescanner.DecodeCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantViewModel @ViewModelInject constructor(
    app: Application,
    clearSessionUseCase: ClearSessionUseCase,
    isUserConnectedUseCase: IsUserConnectedUseCase,
    private val getConfereeByIdUseCase: GetConfereeByIdUseCase,
    private val addConfereeUseCase: AddConfereeUseCase,
    getRestaurantAttendantsCountUseCase: GetRestaurantAttendantsCountUseCase,
    private val checkRestaurantAttendantExistsUseCase: CheckRestaurantAttendantExistsUseCase

) : BaseViewModel
    (
    app,
    clearSessionUseCase,
    isUserConnectedUseCase
) {

    private val _vibrate = SingleEventLiveDataEvent<Boolean>()
    val vibrate: LiveData<Boolean>
        get() = _vibrate

    val _success = SingleEventLiveDataEvent<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    override val isUserConnected: LiveData<Boolean>
        get() = super.isUserConnected


    val restaurantAttendantsCount = getRestaurantAttendantsCountUseCase()
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    init {
        _success.postValue(false)
    }


    fun decodeCallback() = DecodeCallback {

        it.text?.let { id ->
            getConferee(id)
        }

    }

    private fun getConferee(id: String) {

        val onSuccess: suspend (Conferee) -> Unit = {

            withContext(Dispatchers.IO) {
                if (checkRestaurantAttendantExistsUseCase(it.id)) {
                    _vibrate.postValue(true)
                    alertUser("Accées interdit")

                } else {
                    addConfereeUseCase(it)
                    _success.postValue(true)
                    alertUser("Bienvenido ${it.fullName}")
                }

            }

        }

        val action: suspend () -> Conferee = {
            getConfereeByIdUseCase(id)
        }

        runActionWithProgress(action, onSuccess)
    }

    private fun alertUser(message :String){
        viewModelScope.launch(Dispatchers.Main) {
            showToast(message)
        }
    }


}