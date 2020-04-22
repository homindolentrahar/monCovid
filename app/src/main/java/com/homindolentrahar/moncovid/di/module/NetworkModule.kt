package com.homindolentrahar.moncovid.di.module

import com.homindolentrahar.moncovid.BuildConfig
import com.homindolentrahar.moncovid.data.remote.APIService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    fun provideService(retrofit: Retrofit): APIService =
        retrofit.create(APIService::class.java)

    single { provideRetrofit() }
    single { provideService(get()) }
}