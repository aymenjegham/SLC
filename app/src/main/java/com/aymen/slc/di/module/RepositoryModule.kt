package com.aymen.slc.di.module


import com.aymen.slc.data.repository.conferee.ConfereeRepository
import com.aymen.slc.data.repository.conferee.ConfereeRepositoryImpl
import com.aymen.slc.data.repository.device.DeviceRepository
import com.aymen.slc.data.repository.device.DeviceRepositoryImpl
import com.aymen.slc.data.repository.user.UserRepository
import com.aymen.slc.data.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {

    @Binds
    @Reusable
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    @Reusable
    fun bindDeviceRepository(repository: DeviceRepositoryImpl): DeviceRepository

    @Binds
    @Reusable
    fun bindConfereeRepository(repository: ConfereeRepositoryImpl): ConfereeRepository
}