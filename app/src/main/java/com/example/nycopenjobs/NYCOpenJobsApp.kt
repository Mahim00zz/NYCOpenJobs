package com.example.nycopenjobs

import android.app.Application
import android.util.Log
import com.example.nycopenjobs.data.AppContainer
import com.example.nycopenjobs.data.DefaultAppContainer
import com.example.nycopenjobs.util.TAG

class NYCOpenJobsApp : Application() {


    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Application: starting app")
        container = DefaultAppContainer(this)
    }
}