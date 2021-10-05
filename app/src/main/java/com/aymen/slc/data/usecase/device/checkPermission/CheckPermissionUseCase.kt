package com.aymen.slc.data.usecase.device.checkPermission

import com.aymen.slc.data.model.permission.PermissionType
import kotlinx.coroutines.flow.Flow

interface CheckPermissionUseCase {

    operator fun invoke(type: PermissionType): Flow<Boolean>
}