package com.homindolentrahar.moncovid.util

sealed class DataState<T>(
    val data: T? = null,
    val message: String? = null,
    val state: State
) {
    class Success<T>(data: T) : DataState<T>(data, null, State.SUCCESS)
    class Loading<T>(data: T? = null) : DataState<T>(data, null, State.LOADING)
    class Error<T>(message: String, data: T? = null) : DataState<T>(data, message, State.FAILED)
}

enum class State { LOADING, SUCCESS, FAILED }
