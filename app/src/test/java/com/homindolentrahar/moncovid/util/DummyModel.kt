package com.homindolentrahar.moncovid.util

import com.homindolentrahar.moncovid.data.pojo.CovidDailyResponse
import com.homindolentrahar.moncovid.data.pojo.CovidDailyResult
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResponse
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResult

val dummyCovidDailyResult = CovidDailyResult(0, 0, 0, 0, 0, 0, 0, 0)
val dummyCovidProvinceResult = CovidProvinceResult(0, "Provinsi", 0, 0, 0)
val dummyCovidDailyResponse = CovidDailyResponse(listOf(dummyCovidDailyResult))
val dummyCovidProvinceResponse = CovidProvinceResponse(listOf(dummyCovidProvinceResult))