package com.aymen.slc.data.usecase.session.isConnected

import com.aymen.slc.data.repository.user.UserRepository
import javax.inject.Inject

class IsUserConnectedUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) :
    IsUserConnectedUseCase {
    override fun invoke() = userRepository.isUserConnected()

}