package com.pat.flerbi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FlerbiApp:Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin()
    {
        startKoin {
            androidLogger()
            androidContext(this@FlerbiApp)
            modules(com.pat.flerbi.di.modules)
        }
    }

}