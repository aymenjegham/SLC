package com.aymen.slc.di.module

import android.content.Context
import androidx.room.Room
import com.aymen.slc.database.ConfereeDao
import com.aymen.slc.database.DATABASE_NAME
import com.aymen.slc.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Module
    class DatabaseModule {
        @Provides
        fun provideChannelDao(database: Database): ConfereeDao {
            return database.confereeDao()
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): Database {
        return Room.databaseBuilder(
            appContext,
            Database::class.java,
            DATABASE_NAME
        ).build()
    }

}