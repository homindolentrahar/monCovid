package com.homindolentrahar.moncovid.di.module

import android.app.Application
import androidx.room.Room
import com.homindolentrahar.moncovid.model.database.CacheDao
import com.homindolentrahar.moncovid.model.database.CacheDatabase
import com.homindolentrahar.moncovid.util.Constant
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application): CacheDatabase =
        Room.databaseBuilder(application, CacheDatabase::class.java, Constant.DB_NAME)
            .build()

    fun provideDao(database: CacheDatabase):CacheDao =
        database.cacheDao()

    single { Application() }
    single { provideDatabase(get()) }
    single { provideDao(get())}
}