package com.aymen.slc

import android.app.Application
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SLC @Inject constructor() : Application() {


    @Inject
    lateinit var gson: Gson


    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    companion object {
        lateinit var instance: SLC
            private set

    }
}