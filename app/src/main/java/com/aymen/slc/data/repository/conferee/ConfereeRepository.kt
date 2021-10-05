package com.aymen.slc.data.repository.conferee


import com.aymen.slc.data.model.Conferee
import kotlinx.coroutines.flow.Flow

interface ConfereeRepository {

    suspend fun getConfereeById(requestData: MutableMap<String, String>): Conferee

    suspend fun addConferee(articles: Conferee)

    fun getRestaurantAttendantsCount(): Flow<Int>

    suspend fun checkRestaurantAttendantExists(confereeId: String): Boolean


}