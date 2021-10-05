package com.aymen.slc.database


import androidx.room.RoomDatabase
import com.aymen.slc.data.model.Conferee
import com.aymen.slc.global.utils.DATABASE_FILE_NAME
import androidx.room.Database as RoomDatabse


const val DATABASE_NAME = DATABASE_FILE_NAME
const val DATABASE_VERSION = 1

@RoomDatabse(
    entities = [
        Conferee::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)


abstract class Database : RoomDatabase() {

    abstract fun confereeDao(): ConfereeDao


}