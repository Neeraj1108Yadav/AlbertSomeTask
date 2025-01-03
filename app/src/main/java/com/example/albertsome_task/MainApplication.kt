package com.example.albertsome_task

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
    }
}