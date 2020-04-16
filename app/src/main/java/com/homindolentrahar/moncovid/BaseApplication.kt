package com.homindolentrahar.moncovid

import android.app.Application
import com.homindolentrahar.moncovid.di.module.databaseModule
import com.homindolentrahar.moncovid.di.module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        Start injecting dependencies
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                listOf(networkModule, databaseModule)
            )
        }
    }
}