package com.aymen.slc.data.repository.conferee

import com.aymen.slc.data.datasource.api.APIClient
import com.aymen.slc.data.datasource.database.ConfereeDatabase
import com.aymen.slc.data.model.Conferee
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfereeRepositoryImpl @Inject constructor(
    private val api: APIClient,
    private val confereeDatabase: ConfereeDatabase
) :
    ConfereeRepository {

    override suspend fun getConfereeById(requestData: MutableMap<String, String>) =
        api.getConfereeById(requestData)

    override suspend fun addConferee(conferee: Conferee) =
        confereeDatabase.addConferee(conferee)

    override fun getRestaurantAttendantsCount(): Flow<Int> =
        confereeDatabase.getRestaurantsAttendantsCount()

    override suspend fun checkRestaurantAttendantExists(confereeId: String): Boolean =
        confereeDatabase.checkRestaurantAttendantExists(confereeId)


}