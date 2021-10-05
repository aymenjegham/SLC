package com.aymen.slc.data.model.user

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("id")
    val id: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("firstname")
    val firstname: String,

    @SerializedName("lastname")
    val lastname: String,

    @SerializedName("type")
    val type: String
){
    val fullName: String
        get() = "$firstname $lastname"
}