package com.aymen.slc.data.usecase.user.getUser

import com.aymen.slc.data.model.user.User
import kotlinx.coroutines.flow.Flow

interface GetUserUseCase {

    operator fun invoke(): Flow<User?>
}