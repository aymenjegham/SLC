package com.aymen.slc.data.usecase.device.setPermissionRequested

import com.aymen.slc.data.model.permission.PermissionType


interface SetPermissionIsRequestedUseCase {

    suspend operator fun invoke(isRequested: Boolean, permissionType: PermissionType)
}