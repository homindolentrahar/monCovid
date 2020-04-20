package com.homindolentrahar.moncovid.data.remote

import com.homindolentrahar.moncovid.data.pojo.CovidDailyResponse
import com.homindolentrahar.moncovid.data.pojo.CovidOverview
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface APIService {
    @GET("api")
    fun getCovidOverview(): Observable<CovidOverview>

    @GET("api/harian")
    fun getCovidDaily(): Observable<CovidDailyResponse>

    @GET("api/provinsi")
    fun getCovidProvince(): Observable<CovidProvinceResponse>
}