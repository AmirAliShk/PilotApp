package com.example.myapplication.app

import android.app.Activity
import android.app.Application
import android.content.Context

class MyApplication : Application() {

    companion object {
        lateinit var context: Context
        lateinit var currentActivity: Activity
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}