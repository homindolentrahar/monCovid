package com.homindolentrahar.moncovid.di.module

import com.homindolentrahar.moncovid.presenter.main.fragment.viewmodel.MainViewModel
import com.homindolentrahar.moncovid.presenter.main.fragment.viewmodel.ProvinceViewModel
import com.homindolentrahar.moncovid.util.Constant
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(),get(named(Constant.SUBSCRIBER_THREAD)),get(named(Constant.OBSERVER_THREAD))) }
    viewModel { ProvinceViewModel(get(),get(named(Constant.SUBSCRIBER_THREAD)),get(named(Constant.OBSERVER_THREAD))) }
}