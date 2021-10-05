package com.aymen.slc.data.repository.device

import android.content.Context
import android.os.Build
import com.aymen.slc.data.datasource.cache.Cache
import com.aymen.slc.data.model.permission.PermissionType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cache: Cache
) : DeviceRepository {


    override fun isPermissionRequested(type: PermissionType) =
        cache.isPermissionRequested(type.value)

    override suspend fun setPermissionIsRequested(type: PermissionType, isRequested: Boolean) {
        cache.setPermissionIsRequested(type.value, isRequested)
    }

    @Suppress("DEPRECATION")
    private fun getDeviceLocal() =
        context.resources.configuration
            .run {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    locales[0]
                else
                    locale
            }


}