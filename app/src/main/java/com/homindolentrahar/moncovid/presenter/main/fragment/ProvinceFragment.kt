package com.homindolentrahar.moncovid.presenter.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.snackbar.Snackbar
import com.homindolentrahar.moncovid.R
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResult
import com.homindolentrahar.moncovid.presenter.main.adapter.CovidProvinceAdapter
import com.homindolentrahar.moncovid.presenter.main.fragment.viewmodel.ProvinceViewModel
import com.homindolentrahar.moncovid.util.Constant
import com.homindolentrahar.moncovid.util.CustomBarMarkerView
import com.homindolentrahar.moncovid.util.State
import kotlinx.android.synthetic.main.fragment_province.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ProvinceFragment : Fragment() {

    val provinceViewModel by viewModel<ProvinceViewModel>()
    private val entries = mutableListOf<BarEntry>()
    private lateinit var snackbar: Snackbar
    private lateinit var adapter: CovidProvinceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_province, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Snackbar init
        initSnackbar()
//        RecyclerView setup
        initRecyclerView()
//        Data setup
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
            province_wrapper,
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

    private fun initRecyclerView() {
        adapter = CovidProvinceAdapter()

        rv_list_province.adapter = adapter
    }

    private fun initData() {
        provinceViewModel.getCovidProvince()
        provinceViewModel.covidProvince.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState.state) {
                State.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                }
                State.SUCCESS -> {
                    val list = dataState.data
                    val chartList = list?.take(5)
                    initChart(chartList!!)

                    adapter.submitList(list)

                    swipe_refresh.isRefreshing = false
                    progress_bar.visibility = View.GONE
                }
                State.FAILED -> {
                    Log.d(ProvinceFragment::class.java.simpleName, dataState.message.toString())

                    swipe_refresh.isRefreshing = false
                    progress_bar.visibility = View.GONE
                }
            }
        })
    }

    private fun refresh() {
        swipe_refresh.setOnRefreshListener {
            initData()
            checkInternetConnection()
        }
    }

    private fun initChart(listData: List<CovidProvinceResult>) {
        entries.clear()
        var starter = 0f
        for (data in listData) {
            entries.add(
                BarEntry(
                    starter,
                    floatArrayOf(
                        data.positif.toFloat(),
                        data.sembuh.toFloat(),
                        data.meninggal.toFloat()
                    ),
                    data
                )
            )
            starter++
        }

        val setOfData = BarDataSet(entries, "")
        val chartData = BarData(setOfData)
        val yAxis = chart_province.axisLeft
        val marker = CustomBarMarkerView(requireContext(), R.layout.custom_bar_marker)

        setOfData.stackLabels = arrayOf("Confirmed", "Recovered", "Deaths")
        setOfData.colors = listOf(
            requireContext().getColor(R.color.confirmed),
            requireContext().getColor(R.color.recovered),
            requireContext().getColor(R.color.deaths)
        )
        chartData.barWidth = 0.5f
        yAxis.textColor = requireContext().getColor(R.color.white)

        chart_province.data = chartData
        chart_province.marker = marker
        chart_province.description.isEnabled = false
        chart_province.legend.textColor = requireContext().getColor(R.color.white)
        chart_province.setDrawValueAboveBar(false)
        chart_province.setFitBars(true)
        chart_province.animateY(700)
        chart_province.setMaxVisibleValueCount(5)
        chart_province.invalidate()
    }
}
