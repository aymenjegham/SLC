package com.aymen.slc.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Conferee", indices = [Index(value = ["id"], unique = true)])
@Parcelize
data class Conferee(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @Expose
    @SerializedName("id")
    val id: String,

    @ColumnInfo(name = "address")
    @Expose
    @SerializedName("address")
    val address: String,

    @ColumnInfo(name = "congres")
    @Expose
    @SerializedName("congres")
    val congress: String,

    @ColumnInfo(name = "email")
    @Expose
    @SerializedName("email")
    val email: String,

    @ColumnInfo(name = "fullname")
    @Expose
    @SerializedName("fullname")
    val name: String,

    @ColumnInfo(name = "gender")
    @Expose
    @SerializedName("gender")
    val gender: String,

    @ColumnInfo(name = "phone")
    @Expose
    @SerializedName("phone")
    val phone: String,

    @ColumnInfo(name = "status")
    @Expose
    @SerializedName("status")
    val status: String,

    @ColumnInfo(name = "wp_id")
    @Expose
    @SerializedName("wp_id")
    val wp_id: String

): Parcelable   {

    val fullName: String
        get() = "$gender $name"
}