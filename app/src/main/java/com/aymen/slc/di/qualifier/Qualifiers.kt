package com.aymen.slc.di.qualifier

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PicassoHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class APPHttpClient

@Qualifier
annotation class BoundedDatabaseInstance

@Qualifier
annotation class DatabaseFile