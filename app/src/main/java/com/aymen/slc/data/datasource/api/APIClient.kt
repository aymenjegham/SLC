package com.aymen.slc.data.datasource.api

import com.aymen.slc.data.model.Conferee
import com.aymen.slc.data.model.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface APIClient {

    @POST("login")
    suspend fun login(@Body requestData: Map<String, String>): UserResponse

    @POST("getUserById")
    suspend fun getConfereeById(@Body requestData: Map<String, String>): Conferee

}