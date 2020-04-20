package com.homindolentrahar.moncovid.presenter.main.fragment.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResult
import com.homindolentrahar.moncovid.domain.usescase.UsesCase
import com.homindolentrahar.moncovid.util.DataState
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class ProvinceViewModel(
    private val usesCase: UsesCase,
    private val subscriber: Scheduler,
    private val observer: Scheduler
) : ViewModel() {

    private val _covidProvince = MutableLiveData<DataState<List<CovidProvinceResult>>>()
    val covidProvince: LiveData<DataState<List<CovidProvinceResult>>>
        get() = _covidProvince

    @SuppressLint("CheckResult")
    fun getCovidProvince() {
        usesCase.getCovidProvince()
            .subscribeOn(subscriber)
            .observeOn(observer)
            .doOnSubscribe { _covidProvince.value = DataState.Loading() }
            .subscribe(
                { list -> _covidProvince.value = DataState.Success(list) },
                { error -> _covidProvince.value = DataState.Error(error.message.toString()) }
            )
    }
}