package com.aymen.slc.data.usecase.user.login

interface LoginUserUseCase {

    suspend operator fun invoke(email: String, password: String)
}