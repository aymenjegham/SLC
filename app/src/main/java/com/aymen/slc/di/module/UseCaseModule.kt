package com.aymen.slc.di.module

import com.aymen.slc.data.usecase.conferee.addConferee.AddConfereeUseCase
import com.aymen.slc.data.usecase.conferee.addConferee.AddConfereeUseCaseImpl
import com.aymen.slc.data.usecase.conferee.checkRestaurantAttendantExists.CheckRestaurantAttendantExistsUseCase
import com.aymen.slc.data.usecase.conferee.checkRestaurantAttendantExists.CheckRestaurantAttendantExistsUseCaseImpl
import com.aymen.slc.data.usecase.conferee.getConfereeById.GetConfereeByIdUseCase
import com.aymen.slc.data.usecase.conferee.getConfereeById.GetConfereeByIdUserUseCaseImpl
import com.aymen.slc.data.usecase.conferee.getRestaurantAttendants.GetRestaurantAttendantsCountUseCase
import com.aymen.slc.data.usecase.conferee.getRestaurantAttendants.GetRestaurantAttendantsCountUseCaseImpl
import com.aymen.slc.data.usecase.device.checkPermission.CheckPermissionUseCase
import com.aymen.slc.data.usecase.device.checkPermission.CheckPermissionUseCaseImpl
import com.aymen.slc.data.usecase.device.setPermissionRequested.SetPermissionIsRequestedUseCase
import com.aymen.slc.data.usecase.device.setPermissionRequested.SetPermissionIsRequestedUseCaseImpl
import com.aymen.slc.data.usecase.session.clear.ClearSessionUseCase
import com.aymen.slc.data.usecase.session.clear.ClearSessionUseCaseImpl
import com.aymen.slc.data.usecase.session.isConnected.IsUserConnectedUseCase
import com.aymen.slc.data.usecase.session.isConnected.IsUserConnectedUseCaseImpl
import com.aymen.slc.data.usecase.user.getUser.GetUserUseCase
import com.aymen.slc.data.usecase.user.getUser.GetUserUseCaseImpl
import com.aymen.slc.data.usecase.user.login.LoginUserUseCase
import com.aymen.slc.data.usecase.user.login.LoginUserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
interface UseCaseModule {

    @Binds
    fun bindUserLoginUseCase(useCase: LoginUserUseCaseImpl): LoginUserUseCase

    @Binds
    fun bindIsUserConnectedUseCase(useCase: IsUserConnectedUseCaseImpl): IsUserConnectedUseCase

    @Binds
    fun bindsClearSessionUseCase(useCase: ClearSessionUseCaseImpl): ClearSessionUseCase

    @Binds
    fun bindGetUserUseCase(useCase: GetUserUseCaseImpl): GetUserUseCase

    @Binds
    fun bindCheckPermissionUseCase(useCase: CheckPermissionUseCaseImpl): CheckPermissionUseCase

    @Binds
    fun bindSetPermissionRequestedUseCase(useCase: SetPermissionIsRequestedUseCaseImpl): SetPermissionIsRequestedUseCase

    @Binds
    fun bindGetConfereeByIdUseCase(useCase: GetConfereeByIdUserUseCaseImpl): GetConfereeByIdUseCase

    @Binds
    fun bindAddConfereeUseCase(useCase: AddConfereeUseCaseImpl): AddConfereeUseCase

    @Binds
    fun bindGetRestaurantAttendantsUseCase(useCase: GetRestaurantAttendantsCountUseCaseImpl): GetRestaurantAttendantsCountUseCase

    @Binds
    fun bindCheckRestaurantAttendantsUseCase(useCase: CheckRestaurantAttendantExistsUseCaseImpl): CheckRestaurantAttendantExistsUseCase
}
