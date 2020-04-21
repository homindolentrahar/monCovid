package com.homindolentrahar.moncovid.domain.usescase

import com.homindolentrahar.moncovid.data.pojo.CovidDailyResult
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResult
import io.reactivex.Observable

interface UsesCase {
    fun getCovidMainData(): Observable<List<CovidDailyResult>>
    fun getCovidProvinceData(): Observable<List<CovidProvinceResult>>
    fun cacheCovidMainData()
    fun cacheCovidProvinceData()
}