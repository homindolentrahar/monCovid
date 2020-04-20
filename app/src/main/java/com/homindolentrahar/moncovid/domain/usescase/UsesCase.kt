package com.homindolentrahar.moncovid.domain.usescase

import com.homindolentrahar.moncovid.data.pojo.CovidDailyResult
import com.homindolentrahar.moncovid.data.pojo.CovidMainData
import com.homindolentrahar.moncovid.data.pojo.CovidOverview
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResult
import io.reactivex.Completable
import io.reactivex.Observable

interface UsesCase {
    fun getCovidMainData(): Observable<CovidMainData>
    fun getCovidProvince(): Observable<List<CovidProvinceResult>>
    fun getCachedOverview(): Observable<CovidOverview>
    fun getCachedDaily(): Observable<List<CovidDailyResult>>
    fun getCachedProvince(): Observable<List<CovidProvinceResult>>
    fun cacheOverview(item: CovidOverview): Completable
    fun cacheDaily(items: List<CovidDailyResult>)
    fun cacheProvince(items: List<CovidProvinceResult>)
}