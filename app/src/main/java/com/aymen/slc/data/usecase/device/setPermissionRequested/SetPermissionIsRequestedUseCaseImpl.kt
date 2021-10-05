package com.aymen.slc.data.usecase.device.setPermissionRequested

import com.aymen.slc.data.model.permission.PermissionType
import com.aymen.slc.data.repository.device.DeviceRepository

import javax.inject.Inject

class SetPermissionIsRequestedUseCaseImpl @Inject constructor(private val deviceRepository: DeviceRepository) :
    SetPermissionIsRequestedUseCase {


    override suspend fun invoke(isRequested: Boolean, permissionType: PermissionType) =
        deviceRepository.setPermissionIsRequested(permissionType, isRequested)
}