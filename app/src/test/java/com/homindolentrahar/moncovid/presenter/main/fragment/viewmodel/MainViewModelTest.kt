package com.homindolentrahar.moncovid.presenter.main.fragment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.homindolentrahar.moncovid.data.pojo.CovidDailyResult
import com.homindolentrahar.moncovid.domain.usescase.UsesCase
import com.homindolentrahar.moncovid.util.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class MainViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val usesCase = mock<UsesCase>()
    private val observerState = mock<Observer<DataState<List<CovidDailyResult>>>>()
    private val viewModel by lazy {
        MainViewModel(
            usesCase,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @Before
    fun initTest() {
        reset(usesCase, observerState)
    }

    @Test
    fun test_getCovidMainData_success() {
        val response = listOf(dummyCovidDailyResult)
        whenever(usesCase.getCovidMainData())
            .thenReturn(Observable.just(response))

        viewModel.covidMainData.observeForever(observerState)
        viewModel.getCovidMainData()

        val captor = captor<DataState<List<CovidDailyResult>>>()
        val expectedLoading = DataState.Loading(null)
        val expectedSuccess = DataState.Success(response)

        captor.run {
            verify(observerState, times(2)).onChanged(capture())
            val (loading, success) = allValues
            assertEquals(expectedLoading.state, loading.state)
            assertEquals(expectedSuccess.state, success.state)
        }
    }

    @Test
    fun test_getCovidMainData_error() {
        val response = Throwable("Error getting data")
        whenever(usesCase.getCovidMainData())
            .thenReturn(Observable.error(response))

        viewModel.covidMainData.observeForever(observerState)
        viewModel.getCovidMainData()

        val captor = captor<DataState<List<CovidDailyResult>>>()
        val expectedLoading = DataState.Loading(null)
        val expectedError = DataState.Error(response.message.toString(), null)

        captor.run {
            verify(observerState, times(2)).onChanged(capture())
            val (loading, error) = allValues
            assertEquals(expectedLoading.state, loading.state)
            assertEquals(expectedError.state, error.state)
        }
    }

}