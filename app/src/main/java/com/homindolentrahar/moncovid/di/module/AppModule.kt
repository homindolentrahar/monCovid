package com.homindolentrahar.moncovid.di.module

import com.homindolentrahar.moncovid.domain.usescase.UsesCase
import com.homindolentrahar.moncovid.domain.usescase.UsesCaseImpl
import com.homindolentrahar.moncovid.data.database.CacheDao
import com.homindolentrahar.moncovid.data.remote.APIService
import com.homindolentrahar.moncovid.data.repository.Repository
import com.homindolentrahar.moncovid.data.repository.RepositoryImpl
import com.homindolentrahar.moncovid.util.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    fun provideRepository(apiService: APIService, cacheDao: CacheDao): Repository =
        RepositoryImpl(apiService, cacheDao)

    fun provideUsesCase(repository: Repository): UsesCase =
        UsesCaseImpl(repository)

    single { provideRepository(get(), get()) }
    single { provideUsesCase(get()) }
    single { CompositeDisposable() }
    single(named(Constant.SUBSCRIBER_THREAD)) { Schedulers.io() }
    single(named(Constant.OBSERVER_THREAD)) { AndroidSchedulers.mainThread() }
}