package com.aymen.slc.di.module


import com.aymen.slc.data.datasource.database.ConfereeDatabase
import com.aymen.slc.data.datasource.database.ConfereeDatabaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourcesModule {

    @Binds
    abstract fun bindsConfereeDatabase(confereeDatabaseImpl: ConfereeDatabaseImpl): ConfereeDatabase


}