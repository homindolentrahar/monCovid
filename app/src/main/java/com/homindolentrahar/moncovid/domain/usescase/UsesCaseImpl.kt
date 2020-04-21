package com.homindolentrahar.moncovid.domain.usescase

import android.annotation.SuppressLint
import com.homindolentrahar.moncovid.data.pojo.CovidDailyResult
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResult
import com.homindolentrahar.moncovid.data.repository.Repository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class UsesCaseImpl(private val repository: Repository) : UsesCase {

    override fun getCovidMainData(): Observable<List<CovidDailyResult>> {
        return repository.getCachedDaily()
            .flatMap { list ->
                return@flatMap if (list.isEmpty()) {
                    repository.getCovidDaily().map { it.data }
                        .subscribeOn(Schedulers.io())
                        .doOnNext { repository.cacheDaily(it) }
                } else {
                    repository.getCachedDaily()
                }
            }
    }

    override fun getCovidProvinceData(): Observable<List<CovidProvinceResult>> {
        return repository.getCachedProvince()
            .flatMap { list ->
                return@flatMap if (list.isEmpty()) {
                    repository.getCovidProvince().map { it.data }
                        .subscribeOn(Schedulers.io())
                        .doOnNext { list -> repository.cacheProvince(list) }
                } else {
                    repository.getCachedProvince()
                }
            }
    }

    @SuppressLint("CheckResult")
    override fun cacheCovidMainData() {
        repository.getCovidDaily().map { it.data }
            .subscribeOn(Schedulers.io())
            .subscribe { list -> repository.cacheDaily(list) }
    }

    @SuppressLint("CheckResult")
    override fun cacheCovidProvinceData() {
        repository.getCovidProvince().map { it.data }
            .subscribeOn(Schedulers.io())
            .subscribe { list -> repository.cacheProvince(list) }
    }
}