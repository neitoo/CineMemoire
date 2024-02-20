package com.neito.cinememoire

import android.app.Application
import com.neito.cinememoire.presentation.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}