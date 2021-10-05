package com.aymen.slc.data.usecase.device.checkPermission

import com.aymen.slc.data.model.permission.PermissionType
import com.aymen.slc.data.repository.device.DeviceRepository

import javax.inject.Inject

class CheckPermissionUseCaseImpl @Inject constructor(private val deviceRepository: DeviceRepository) :
    CheckPermissionUseCase {


    override fun invoke(type: PermissionType) = deviceRepository.isPermissionRequested(type)
}