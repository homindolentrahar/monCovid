package com.homindolentrahar.moncovid.di.module

import android.content.Context
import com.homindolentrahar.moncovid.BuildConfig
import com.homindolentrahar.moncovid.data.remote.APIService
import com.homindolentrahar.moncovid.util.Constant
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    fun provideClient(context: Context): OkHttpClient {
        val cache = Cache(context.cacheDir, 10 * 1024 * 1024)
        return OkHttpClient.Builder()
            .cache(cache)
//            .addInterceptor { chain ->
//                var request = chain.request()
//                request = if (Constant.isNetworkAvailable(context)) {
//                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
//                } else {
//                    request.newBuilder().header(
//                        "Cache-Control",
//                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
//                    ).build()
//                }
//                chain.proceed(request)
//            }
            .build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    fun provideService(retrofit: Retrofit): APIService =
        retrofit.create(APIService::class.java)

    single { provideClient(get()) }
    single { provideRetrofit(get()) }
    single { provideService(get()) }
}