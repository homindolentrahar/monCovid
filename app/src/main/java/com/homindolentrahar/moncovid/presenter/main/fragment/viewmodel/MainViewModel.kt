package com.homindolentrahar.moncovid.presenter.main.fragment.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homindolentrahar.moncovid.data.pojo.CovidDailyResult
import com.homindolentrahar.moncovid.domain.usescase.UsesCase
import com.homindolentrahar.moncovid.util.DataState
import io.reactivex.Scheduler

class MainViewModel(
    private val usesCase: UsesCase,
    private val subscriber: Scheduler,
    private val observer: Scheduler
) : ViewModel() {

    private val _covidMainData = MutableLiveData<DataState<List<CovidDailyResult>>>()
    val covidMainData: LiveData<DataState<List<CovidDailyResult>>>
        get() = _covidMainData

    @SuppressLint("CheckResult")
    fun getCovidMainData() {
        usesCase.getCovidMainData()
            .subscribeOn(subscriber)
            .observeOn(observer)
            .doOnSubscribe { _covidMainData.value = DataState.Loading() }
            .subscribe(
                { list -> _covidMainData.value = DataState.Success(list.sortedByDescending { it.hariKe }) },
                { error -> _covidMainData.value = DataState.Error(error.message.toString()) }
            )
    }
}