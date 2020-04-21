package com.homindolentrahar.moncovid.domain.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.homindolentrahar.moncovid.domain.usescase.UsesCase
import org.koin.java.KoinJavaComponent.inject
import java.io.IOException

class SyncDataWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    val usesCase by inject(UsesCase::class.java)

    override fun doWork(): Result {
        return try {
            usesCase.cacheCovidMainData()
            usesCase.cacheCovidProvinceData()
            Result.success()
        } catch (e: IOException) {
            Result.retry()
        }
    }
}