package com.aymen.slc.data.repository.user

import com.aymen.slc.data.datasource.api.APIClient
import com.aymen.slc.data.datasource.cache.Cache
import com.aymen.slc.data.model.user.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: APIClient, private val cache: Cache) :
    UserRepository {

    override suspend fun login(requestData: MutableMap<String, String>) =
        api.login(requestData)


    override suspend fun setSessionData(token: String, user: User) {
        cache.setToken(token)
        cache.setUser(user)
    }

    override suspend fun clearSession() = cache.clearSessionData()

    override fun isUserConnected() = cache.isUserConnected()

    override fun getUser() = cache.getUser()
}