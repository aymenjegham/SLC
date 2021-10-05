package com.aymen.slc.data.usecase.user.getUser

import com.aymen.slc.data.repository.user.UserRepository
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor(private val userRepository: UserRepository) :
    GetUserUseCase {
    override fun invoke() = userRepository.getUser()
}