package com.aymen.slc.di.module


import com.aymen.slc.database.ConfereeDao
import com.aymen.slc.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class DaoModule {


    @Provides
    fun provideConfereeDao(database: Database): ConfereeDao {
        return database.confereeDao()
    }


}