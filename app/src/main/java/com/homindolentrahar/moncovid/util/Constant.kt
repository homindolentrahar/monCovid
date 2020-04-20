package com.homindolentrahar.moncovid.util

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.Display
import android.view.WindowManager
import android.widget.TextView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

object Constant {
    const val OVERVIEW_TABLE = "overview_table"
    const val DAILY_TABLE = "daily_table"
    const val PROVINCE_TABLE = "province_table"
    const val DB_NAME = "covid.db"
    const val SUBSCRIBER_THREAD = "Subscriber"
    const val OBSERVER_THREAD = "Observer"

    @SuppressLint("SimpleDateFormat")
    fun getDateFromTimestamp(timestamp: Long): String {
        return SimpleDateFormat("MMM dd, yyyy").format(Date(timestamp))
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return if (networkCapabilities != null) {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )
        } else {
            false

        }
    }

    fun isInternetAvailable(): Single<Boolean> {
        return Single.fromCallable {
            try {
                val timeout = 1500
                val socket = Socket()
                val address = InetSocketAddress("8.8.8.8", 53)

                socket.connect(address, timeout)
                socket.close()

                true
            } catch (e: IOException) {
                false
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun animateTextViewCount(textView: TextView, number: Int) {
        val animator = ValueAnimator()
        animator.setObjectValues(0, number)
        animator.addUpdateListener { textView.text = it.animatedValue.toString() }
        animator.duration = 700
        animator.start()
    }

    fun getNavBarSize(context: Context): Point {
        val appUsableSize = getAppUsableScreen(context)
        val realScreenSize = getRealScreenSize(context)

//        NavBar in the right
        if (appUsableSize.x < realScreenSize.x) {
            return Point(realScreenSize.x - appUsableSize.x, appUsableSize.y)
        }
//        NavBar in the bottom
        if (appUsableSize.y < realScreenSize.y) {
            return Point(appUsableSize.x, realScreenSize.y - appUsableSize.y)
        }
//        No NavBar present
        return Point()
    }

    private fun getAppUsableScreen(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

    private fun getRealScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size)
        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                size.x = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
                size.y = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
            } catch (e: IllegalAccessException) {

            }
        }
        return size
    }
}