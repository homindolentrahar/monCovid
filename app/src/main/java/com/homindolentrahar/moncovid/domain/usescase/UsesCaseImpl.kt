package com.homindolentrahar.moncovid.domain.usescase

import com.homindolentrahar.moncovid.model.pojo.CovidDailyResult
import com.homindolentrahar.moncovid.model.pojo.CovidOverview
import com.homindolentrahar.moncovid.model.pojo.CovidProvinceResult
import com.homindolentrahar.moncovid.model.repository.Repository
import io.reactivex.Completable
import io.reactivex.Observable

class UsesCaseImpl(private val repository: Repository) : UsesCase {
    override fun getCovidOverview(): Observable<CovidOverview> = repository.getCovidOverview()

    override fun getCovidDaily(): Observable<List<CovidDailyResult>> =
        repository.getCovidDaily().map { response -> response.data }

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