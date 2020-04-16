package com.homindolentrahar.moncovid.domain.usescase

import com.homindolentrahar.moncovid.model.pojo.CovidDailyResult
import com.homindolentrahar.moncovid.model.pojo.CovidOverview
import com.homindolentrahar.moncovid.model.pojo.CovidProvinceResult
import io.reactivex.Completable
import io.reactivex.Observable

interface UsesCase {
    fun getCovidOverview(): Observable<CovidOverview>
    fun getCovidDaily(): Observable<List<CovidDailyResult>>
    fun getCovidProvince(): Observable<List<CovidProvinceResult>>
    fun getCachedOverview(): Observable<CovidOverview>
    fun getCachedDaily(): Observable<List<CovidDailyResult>>
    fun getCachedProvince(): Observable<List<CovidProvinceResult>>
    fun cacheOverview(item: CovidOverview): Completable
    fun cacheDaily(items: List<CovidDailyResult>)
    fun cacheProvince(items: List<CovidProvinceResult>)
}