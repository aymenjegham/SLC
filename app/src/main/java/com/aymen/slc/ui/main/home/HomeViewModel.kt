package com.aymen.slc.ui.main.home

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aymen.slc.R
import com.aymen.slc.data.model.permission.PermissionType
import com.aymen.slc.data.model.user.UsersType
import com.aymen.slc.data.usecase.device.checkPermission.CheckPermissionUseCase
import com.aymen.slc.data.usecase.device.setPermissionRequested.SetPermissionIsRequestedUseCase
import com.aymen.slc.data.usecase.session.clear.ClearSessionUseCase
import com.aymen.slc.data.usecase.session.isConnected.IsUserConnectedUseCase
import com.aymen.slc.data.usecase.user.getUser.GetUserUseCase
import com.aymen.slc.global.helpers.DebugLog
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.global.helpers.SingleEventLiveDataEvent
import com.aymen.slc.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @ViewModelInject constructor(
    app: Application,
    getUserUseCase: GetUserUseCase,
    clearSessionUseCase: ClearSessionUseCase,
    isUserConnectedUseCase: IsUserConnectedUseCase,
    private val checkPermissionUseCase: CheckPermissionUseCase,
    private val setPermissionIsRequestedUseCase: SetPermissionIsRequestedUseCase
) : BaseViewModel
    (
    app,
    clearSessionUseCase,
    isUserConnectedUseCase
) {

    var permissionType: PermissionType = PermissionType.CAMERA

    private var permissionIsRequested: Boolean? = null

    var permissionGranted: Boolean = false

    private val _requestPermission = SingleEventLiveDataEvent<PermissionType>()
    val requestPermission: LiveData<PermissionType>
        get() = _requestPermission

    private val _checkPermission = SingleEventLiveDataEvent<Boolean>()
    val checkPermission: LiveData<Boolean>
        get() = _checkPermission


    override val isUserConnected: LiveData<Boolean>
        get() = super.isUserConnected

    private val user = getUserUseCase()
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    val roles = Transformations.map(user) {
        it?.type?.split(",")?.map {
            it.toInt()
        }
    }

    init {
        checkPermission()
    }


    fun navigate(): (Int) -> Unit = {

        roles.value?.get(it)?.let { type ->

            if (permissionGranted) {
                when (UsersType.from(type)) {

                    UsersType.SUPER_ADMIN -> navigate(Navigation.ToSuperAdmin)

                    UsersType.SECRETARIAT -> navigate(Navigation.ToSecretariat)

                    UsersType.WORKSHOP_ACCESS_CONTROL -> navigate(Navigation.ToWorkshop)

                    UsersType.HOTEL_ACCESS_CONTROL -> navigate(Navigation.ToHotel)

                    UsersType.EVENT_ACCESS_CONTROL -> navigate(Navigation.ToEvent)

                    UsersType.RESTAURANT -> navigate(Navigation.ToRestaurant)

                    UsersType.CONFERENCE -> navigate(Navigation.ToConference)
                }

            } else {
                checkPermission()
            }

        }
    }

    fun handlePermissionState(
        isGranted: Boolean?,
        showDialog: Boolean
    ) {
        if (isGranted == true) {
            onPermissionGranted(permissionType)
        } else if (isGranted != true) {
            onPermissionNotGranted()
            if (showDialog) {
                val title = context.getString(R.string.permission_required)
                val message =
                    "${context.getString(R.string.app_name)} ${getPermissionDialogStatement()}"
                val ok = context.getString(R.string.allow)
                val okAction = {
                    when (isGranted) {
                        true -> {
                            _requestPermission.value = permissionType
                        }
                        false -> {
                            navigate(Navigation.Settings)
                        }
                    }
                }

                if (permissionType == PermissionType.LOCATION) {
                    showSimpleDialog(title, message, ok, okAction = okAction)

                } else {
                    showChooseDialog(
                        title,
                        message,
                        ok,
                        okAction = okAction,
                        cancelAction = ::onPermissionDeclined
                    )
                }
            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    if (!getPermissionIsRequested()) {
                        setPermissionIsRequested(true)
                    }
                }

            }
        }

    }

    private fun checkPermission() {
        viewModelScope.launch {
            val isRequested = withContext(Dispatchers.IO) { getPermissionIsRequested() }
            _checkPermission.value = isRequested ?: false

        }
    }

    private suspend fun setPermissionIsRequested(isRequested: Boolean) {
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            setPermissionIsRequestedUseCase(isRequested, permissionType)
        }.also { permissionIsRequested = isRequested }

    }

    private suspend fun getPermissionIsRequested(): Boolean =
        permissionIsRequested ?: let {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                checkPermissionUseCase(permissionType).first()
            }.also {
                permissionIsRequested = it
            }

        }

    private fun getPermissionDialogStatement() =
        when (permissionType) {
            PermissionType.ACCESS_DATA -> R.string.access_files_permission_statement
            PermissionType.CAMERA -> R.string.camera_permission_statement
            else -> R.string.gps_permission_statement
        }.let { context.getString(it) }


    private fun onPermissionDeclined() {}

    private fun onPermissionGranted(permissionType: PermissionType) {
        permissionGranted = true
    }

    private fun onPermissionNotGranted() {}
}