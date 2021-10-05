package com.aymen.slc.data.datasource.database


import com.aymen.slc.data.model.Conferee
import com.aymen.slc.database.ConfereeDao
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfereeDatabaseImpl @Inject constructor(
        private val confereeDao: ConfereeDao,
        private val gson: Gson

) : ConfereeDatabase {


        override suspend fun addConferee(conferee: Conferee)  =
                confereeDao.insert(conferee)

        override fun getRestaurantsAttendantsCount(): Flow<Int> =
                confereeDao.getRestaurantAttendantsCount()

        override suspend fun checkRestaurantAttendantExists(confereeId: String): Boolean =
                confereeDao.checkRestaurantAttendantExists(confereeId)


}