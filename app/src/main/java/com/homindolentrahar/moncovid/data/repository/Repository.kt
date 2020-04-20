package com.homindolentrahar.moncovid.data.repository

import com.homindolentrahar.moncovid.data.pojo.*
import io.reactivex.Completable
import io.reactivex.Observable

interface Repository {
    fun getCovidOverview(): Observable<CovidOverview>
    fun getCovidDaily(): Observable<CovidDailyResponse>
    fun getCovidProvince(): Observable<CovidProvinceResponse>
    fun getCachedOverview(): Observable<CovidOverview>
    fun getCachedDaily(): Observable<List<CovidDailyResult>>
    fun getCachedProvince(): Observable<List<CovidProvinceResult>>
    fun cacheOverview(item: CovidOverview): Completable
    fun cacheDaily(items: List<CovidDailyResult>)
    fun cacheProvince(items: List<CovidProvinceResult>)
}