package com.aymen.slc.data.datasource.cache


import com.aymen.slc.data.model.user.User
import kotlinx.coroutines.flow.Flow

interface Cache {

    fun isUserConnected(): Flow<Boolean>

    fun getToken(): String?

    suspend fun setToken(token: String)

    suspend fun setRefreshToken(token: String)

    suspend fun clearSessionData()

    suspend fun setUser(user: User)

    fun getUser(): Flow<User?>

    suspend fun setPermissionIsRequested(permissionKey: String, isRequested: Boolean)

    fun isPermissionRequested(permissionKey: String): Flow<Boolean>


}