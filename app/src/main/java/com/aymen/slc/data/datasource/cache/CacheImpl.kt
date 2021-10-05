package com.aymen.slc.data.datasource.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.remove
import com.aymen.slc.data.model.user.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


const val TOKEN_KEY = "token"
const val REFRESH_TOKEN_KEY = "refresh_token"
const val USER_KEY = "user"

class CacheImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) : Cache {


    override fun isUserConnected(): Flow<Boolean> = dataStore.data.map {
        it[preferencesKey<String>(TOKEN_KEY)] != null
    }

    override fun getToken(): String? =
        runBlocking { dataStore.data.map { it[preferencesKey<String>(TOKEN_KEY)] }.first() }

    override suspend fun setToken(token: String) {
        dataStore.edit {
            it[preferencesKey(TOKEN_KEY)] = token
        }
    }

    override suspend fun setRefreshToken(token: String) {
        dataStore.edit {
            it[preferencesKey(REFRESH_TOKEN_KEY)] = token
        }
    }

    override suspend fun clearSessionData() {
        dataStore.edit {
            it.clear()
            it.remove(preferencesKey<String>(TOKEN_KEY))
            it.remove(preferencesKey<String>(USER_KEY))
            it.remove(preferencesKey<String>(REFRESH_TOKEN_KEY))
        }
    }

    override suspend fun setUser(user: User) {
        dataStore.edit {
            it[preferencesKey(USER_KEY)] = gson.toJson(user)
        }
    }

    override fun getUser() =
        dataStore.data.map { gson.fromJson(it[preferencesKey<String>(USER_KEY)], User::class.java) }


    override suspend fun setPermissionIsRequested(permissionKey: String, isRequested: Boolean) {
        dataStore.edit { it[preferencesKey(permissionKey)] = isRequested }
    }

    override fun isPermissionRequested(permissionKey: String): Flow<Boolean> =
        dataStore.data.map { it[preferencesKey<Boolean>(permissionKey)] ?: false }


}