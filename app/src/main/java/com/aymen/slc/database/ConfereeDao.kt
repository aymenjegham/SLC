package com.aymen.slc.database

import androidx.room.*
import com.aymen.slc.data.model.Conferee
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfereeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(conferee: Conferee)


    @Query("""SELECT COUNT(*) FROM Conferee""")
    fun getRestaurantAttendantsCount(): Flow<Int>

    @Query("SELECT EXISTS(SELECT * FROM Conferee WHERE id = :confereeId)")
    fun checkRestaurantAttendantExists(confereeId: String): Boolean


}