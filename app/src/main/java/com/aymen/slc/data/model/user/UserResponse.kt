package com.aymen.slc.data.model.user

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("expires_in")
    val expiresIn: Int,

    @SerializedName("user")
    val user: User
)