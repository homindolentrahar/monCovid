package com.homindolentrahar.moncovid.domain.usescase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.homindolentrahar.moncovid.data.repository.Repository
import com.homindolentrahar.moncovid.util.dummyCovidDailyResult
import com.homindolentrahar.moncovid.util.dummyCovidProvinceResult
import com.homindolentrahar.moncovid.util.mock
import com.homindolentrahar.moncovid.util.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test

class UsesCaseImplTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val repository = mock<Repository>()
    private val usesCase by lazy { UsesCaseImpl(repository) }

    @Test
    fun test_getCovidMainData_complete() {
        whenever(repository.getCachedDaily())
            .thenReturn(Observable.just(listOf(dummyCovidDailyResult)))

        usesCase.getCovidMainData()
            .test()
            .assertComplete()
    }

    @Test
    fun test_getCovidMainData_error() {
        val response = Throwable("Error getting cached data")
        whenever(repository.getCachedDaily())
            .thenReturn(Observable.error(response))

        usesCase.getCovidMainData()
            .test()
            .assertError(response)
    }

    @Test
    fun test_getCovidMainData_value() {
        val response = listOf(dummyCovidDailyResult)
        whenever(repository.getCachedDaily())
            .thenReturn(Observable.just(response))

        usesCase.getCovidMainData()
            .test()
            .assertValue(response)
    }

    @Test
    fun test_getCovidProvinceData_complete() {
        whenever(repository.getCachedProvince())
            .thenReturn(Observable.just(listOf(dummyCovidProvinceResult)))

        usesCase.getCovidProvinceData()
            .test()
            .assertComplete()
    }

    @Test
    fun test_getCovidProvinceData_error() {
        val response = Throwable("Error getting cached data")
        whenever(repository.getCachedProvince())
            .thenReturn(Observable.error(response))

        usesCase.getCovidProvinceData()
            .test()
            .assertError(response)
    }

    @Test
    fun test_getCovidProvinceData_value() {
        val response = listOf(dummyCovidProvinceResult)
        whenever(repository.getCachedProvince())
            .thenReturn(Observable.just(response))

        usesCase.getCovidProvinceData()
            .test()
            .assertValue(response)
    }

}