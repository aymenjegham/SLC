package com.aymen.slc.data.repository.device

import com.aymen.slc.data.model.permission.PermissionType
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {


    fun isPermissionRequested(type: PermissionType): Flow<Boolean>

    suspend fun setPermissionIsRequested(type: PermissionType, isRequested: Boolean)
}