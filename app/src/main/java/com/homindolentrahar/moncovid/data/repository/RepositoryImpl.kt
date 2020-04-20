package com.homindolentrahar.moncovid.data.repository

import com.homindolentrahar.moncovid.data.database.CacheDao
import com.homindolentrahar.moncovid.data.pojo.*
import com.homindolentrahar.moncovid.data.remote.APIService
import io.reactivex.Completable
import io.reactivex.Observable

class RepositoryImpl(private val apiService: APIService, private val cacheDao: CacheDao) :
    Repository {
    override fun getCovidOverview(): Observable<CovidOverview> = apiService.getCovidOverview()

    override fun getCovidDaily(): Observable<CovidDailyResponse> = apiService.getCovidDaily()

    override fun getCovidProvince(): Observable<CovidProvinceResponse> =
        apiService.getCovidProvince()

    override fun getCachedOverview(): Observable<CovidOverview> = cacheDao.getCachedOverview()

    override fun getCachedDaily(): Observable<List<CovidDailyResult>> = cacheDao.getCachedDaily()

    override fun getCachedProvince(): Observable<List<CovidProvinceResult>> =
        cacheDao.getCachedProvince()

    override fun cacheOverview(item: CovidOverview): Completable = cacheDao.cacheOverview(item)

    override fun cacheDaily(items: List<CovidDailyResult>) = cacheDao.cacheDaily(items)

    override fun cacheProvince(items: List<CovidProvinceResult>) = cacheDao.cacheProvince(items)
}