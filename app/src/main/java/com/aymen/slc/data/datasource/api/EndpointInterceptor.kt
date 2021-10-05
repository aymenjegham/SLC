package com.aymen.slc.data.datasource.api

import android.content.Context
import com.aymen.slc.data.datasource.cache.Cache
import com.aymen.slc.global.extension.isNetworkAvailable
import com.aymen.slc.global.utils.NoInternetException

import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


private const val TOKEN = "Authorization"
private const val DEVICE_ID = "Device-Id"
private const val OS_TYPE = "OS-Type"
private const val APP_VERSION = "Api-Version"
private const val LANGUAGE = "Language"

@Singleton
class EndpointInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cache: Cache
) : Interceptor {

    @Throws(NoInternetException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (context.isNetworkAvailable()) {
            val requestBuilder = request.newBuilder()

            cache.getToken()?.let {

                requestBuilder
                    .addHeader(TOKEN, it)
                    .build()
            }


            request = requestBuilder.build()

        } else {
            throw NoInternetException("No network available")
        }

        return chain.proceed(request)


    }
}