package com.homindolentrahar.moncovid.presenter.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.snackbar.Snackbar
import com.homindolentrahar.moncovid.R
import com.homindolentrahar.moncovid.presenter.main.adapter.CovidDailyAdapter
import com.homindolentrahar.moncovid.presenter.main.fragment.viewmodel.MainViewModel
import com.homindolentrahar.moncovid.util.Constant
import com.homindolentrahar.moncovid.util.State
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    val mainViewModel by viewModel<MainViewModel>()
    private val entries = mutableListOf<PieEntry>()
    private lateinit var snackbar: Snackbar
    private lateinit var adapter: CovidDailyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Snackbar init
        initSnackbar()
//        RecyclerView setup
        initRecyclerView()
//        Data Setup
        initData()
//        Refresh Data
        refresh()
    }

    override fun onResume() {
        super.onResume()
//        Check Internet connection
        checkInternetConnection()
    }

    private fun initSnackbar() {
        snackbar = Snackbar.make(
            main_wrapper,
            getString(R.string.warning_internet_unavailable),
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setBackgroundTint(requireContext().getColor(R.color.danger))
        snackbar.setTextColor(requireContext().getColor(R.color.white))
    }

    @SuppressLint("CheckResult")
    private fun checkInternetConnection() {
        Constant.isInternetAvailable()
            .subscribe { isConnected ->
                if (!isConnected) {
                    snackbar.show()
                } else {
                    snackbar.dismiss()
                }
            }
    }

    private fun initData() {
        mainViewModel.getCovidMainData()
        mainViewModel.covidMainData.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState.state) {
                State.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                }
                State.SUCCESS -> {
                    val confirmed = if (dataState.data?.get(0)?.kasusKumulatif == 0) {
                        dataState.data[1].kasusKumulatif
                    } else {
                        dataState.data?.get(0)?.kasusKumulatif
                    }
                    val recovered = if (dataState.data?.get(0)?.sembuh == 0) {
                        dataState.data[1].sembuh
                    } else {
                        dataState.data?.get(0)?.sembuh
                    }
                    val deaths = if (dataState.data?.get(0)?.meninggal == 0) {
                        dataState.data[1].meninggal
                    } else {
                        dataState.data?.get(0)?.meninggal
                    }
                    initChart(confirmed!!, recovered!!, deaths!!)

                    val list = dataState.data
                    adapter.submitList(list)

                    swipe_refresh.isRefreshing = false
                    progress_bar.visibility = View.GONE
                }
                State.FAILED -> {
                    Log.d(MainFragment::class.java.simpleName, dataState.message.toString())

                    swipe_refresh.isRefreshing = false
                    progress_bar.visibility = View.GONE
                }
            }
        })
    }

    private fun initRecyclerView() {
        adapter = CovidDailyAdapter()

        rv_list_daily.adapter = adapter
    }

    private fun initChart(confirmed: Int, recovered: Int, deaths: Int) {
        val totalSum = (confirmed + recovered + deaths).toFloat()
        val confirmedChart = convertDataToChart(totalSum, confirmed.toFloat())
        val recoveredChart = convertDataToChart(totalSum, recovered.toFloat())
        val deathsChart = convertDataToChart(totalSum, deaths.toFloat())

        entries.clear()
        entries.add(PieEntry(confirmedChart, "Confirmed"))
        entries.add(PieEntry(recoveredChart, "Recovered"))
        entries.add(PieEntry(deathsChart, "Deaths"))

        val set = PieDataSet(entries, "Overview")
        val data = PieData(set)

        set.setDrawValues(false)
        set.setColors(
            requireContext().getColor(R.color.confirmed),
            requireContext().getColor(R.color.recovered),
            requireContext().getColor(R.color.deaths)
        )

        chart_overview.data = data
        chart_overview.holeRadius = 70f
        chart_overview.description.isEnabled = false
        chart_overview.legend.isEnabled = false
        chart_overview.animateY(700)
        chart_overview.setDrawSliceText(false)
        chart_overview.setDrawEntryLabels(false)
        chart_overview.setHoleColor(requireContext().getColor(R.color.primaryDark))
        chart_overview.invalidate()

        Constant.animateTextViewCount(tv_confirmed, confirmed)
        Constant.animateTextViewCount(tv_recovered, recovered)
        Constant.animateTextViewCount(tv_deaths, deaths)
    }

    private fun refresh() {
        swipe_refresh.setOnRefreshListener {
            initData()
            checkInternetConnection()
        }
    }

    private fun convertDataToChart(totalSum: Float, value: Float): Float {
        return (value / totalSum) * 100
    }
}
