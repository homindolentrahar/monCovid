package com.homindolentrahar.moncovid.presenter.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.homindolentrahar.moncovid.R
import com.homindolentrahar.moncovid.domain.worker.SyncDataWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Setup Navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        NavigationUI.setupWithNavController(bottom_nav, navController)
//        Setup Sync Worker
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()
        val syncWorker = OneTimeWorkRequest.Builder(SyncDataWorker::class.java)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueue(syncWorker)
    }
}
