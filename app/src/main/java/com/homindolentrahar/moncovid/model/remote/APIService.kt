package com.homindolentrahar.moncovid.model.remote

import com.homindolentrahar.moncovid.model.pojo.CovidDailyResponse
import com.homindolentrahar.moncovid.model.pojo.CovidOverview
import com.homindolentrahar.moncovid.model.pojo.CovidProvinceResponse
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