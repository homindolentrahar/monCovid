package com.homindolentrahar.moncovid.data.repository

import com.homindolentrahar.moncovid.data.pojo.CovidDailyResponse
import com.homindolentrahar.moncovid.data.pojo.CovidDailyResult
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResponse
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResult
import io.reactivex.Observable

interface Repository {
    fun getCovidDaily(): Observable<CovidDailyResponse>
    fun getCovidProvince(): Observable<CovidProvinceResponse>
    fun getCachedDaily(): Observable<List<CovidDailyResult>>
    fun getCachedProvince(): Observable<List<CovidProvinceResult>>
    fun cacheDaily(items: List<CovidDailyResult>)
    fun cacheProvince(items: List<CovidProvinceResult>)
}