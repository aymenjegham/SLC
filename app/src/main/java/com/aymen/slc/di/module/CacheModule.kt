package com.aymen.slc.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.aymen.slc.data.datasource.cache.Cache
import com.aymen.slc.data.datasource.cache.CacheImpl
import com.aymen.slc.global.utils.CACHE_FILE_NAME
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class CacheModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context) =
        context.createDataStore(CACHE_FILE_NAME)

    @Singleton
    @Provides
    fun provideCache(dataStore: DataStore<Preferences>, gson: Gson): Cache = CacheImpl(dataStore, gson)

}