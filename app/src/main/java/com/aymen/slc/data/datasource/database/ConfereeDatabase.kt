package com.aymen.slc.data.datasource.database

import com.aymen.slc.data.model.Conferee
import kotlinx.coroutines.flow.Flow


interface ConfereeDatabase {

    suspend fun addConferee(conferee: Conferee)

    fun getRestaurantsAttendantsCount(): Flow<Int>

    suspend fun checkRestaurantAttendantExists(confereeId: String): Boolean

}