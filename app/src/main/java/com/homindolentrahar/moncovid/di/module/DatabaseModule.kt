package com.homindolentrahar.moncovid.di.module

import android.content.Context
import androidx.room.Room
import com.homindolentrahar.moncovid.data.database.CacheDao
import com.homindolentrahar.moncovid.data.database.CacheDatabase
import com.homindolentrahar.moncovid.util.Constant
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(context: Context): CacheDatabase =
        Room.databaseBuilder(context, CacheDatabase::class.java, Constant.DB_NAME)
            .build()

    fun provideDao(database: CacheDatabase):CacheDao =
        database.cacheDao()

    single { provideDatabase(get()) }
    single { provideDao(get())}
}