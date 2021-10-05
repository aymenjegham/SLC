package com.aymen.slc.data.usecase.session.clear

import com.aymen.slc.data.repository.user.UserRepository
import javax.inject.Inject

class ClearSessionUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) :
    ClearSessionUseCase {
    override suspend fun invoke() = userRepository.clearSession()

}