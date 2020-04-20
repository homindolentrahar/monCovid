package com.homindolentrahar.moncovid.domain.usescase

import com.homindolentrahar.moncovid.data.pojo.*
import com.homindolentrahar.moncovid.data.repository.Repository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class UsesCaseImpl(private val repository: Repository) : UsesCase {
    override fun getCovidMainData(): Observable<CovidMainData> =
        Observable.zip(
            repository.getCovidOverview().subscribeOn(Schedulers.io()),
            repository.getCovidDaily().subscribeOn(Schedulers.io()),
            BiFunction<CovidOverview, CovidDailyResponse, CovidMainData> { overview, daily ->
                val list = daily.data.sortedByDescending { it.hariKe }
                CovidMainData(overview, list)
            })

    override fun getCovidProvince(): Observable<List<CovidProvinceResult>> =
        repository.getCovidProvince().map { response -> response.data }

    override fun getCachedOverview(): Observable<CovidOverview> = repository.getCachedOverview()

    override fun getCachedDaily(): Observable<List<CovidDailyResult>> = repository.getCachedDaily()

    override fun getCachedProvince(): Observable<List<CovidProvinceResult>> =
        repository.getCachedProvince()

    override fun cacheOverview(item: CovidOverview): Completable = repository.cacheOverview(item)

    override fun cacheDaily(items: List<CovidDailyResult>) = repository.cacheDaily(items)

    override fun cacheProvince(items: List<CovidProvinceResult>) = repository.cacheProvince(items)
}