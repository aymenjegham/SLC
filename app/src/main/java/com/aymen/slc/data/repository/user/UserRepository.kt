package com.aymen.slc.data.repository.user


import com.aymen.slc.data.model.user.User
import com.aymen.slc.data.model.user.UserResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(requestData: MutableMap<String, String>): UserResponse

    suspend fun setSessionData(token: String, user: User)

    suspend fun clearSession()

    fun isUserConnected() : Flow<Boolean>

    fun getUser() : Flow<User?>

}