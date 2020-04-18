package com.homindolentrahar.moncovid.presenter.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.homindolentrahar.moncovid.R
import com.homindolentrahar.moncovid.model.pojo.CovidProvinceResult
import com.homindolentrahar.moncovid.util.CustomBarMarkerView
import kotlinx.android.synthetic.main.fragment_province.*

/**
 * A simple [Fragment] subclass.
 */
class ProvinceFragment : Fragment() {

    private val entries = mutableListOf<BarEntry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_province, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Refresh Data
        refresh()
//        Chart setup
        initChart()
    }

    private fun refresh() {
        swipe_refresh.setOnRefreshListener {
            Toast.makeText(requireContext(), "Refreshing", Toast.LENGTH_SHORT).show()
            swipe_refresh.isRefreshing = false
        }
    }

    private fun initChart() {
        entries.add(
            BarEntry(
                0f,
                floatArrayOf(345f, 124f, 90f),
                CovidProvinceResult(1, "Jakarta", 345, 124, 90)
            )
        )
        entries.add(
            BarEntry(
                1f,
                floatArrayOf(460f, 120f, 80f),
                CovidProvinceResult(2, "Jawa Barat", 460, 120, 80)
            )
        )
        entries.add(
            BarEntry(
                2f,
                floatArrayOf(189f, 115f, 50f),
                CovidProvinceResult(3, "Jawa Tengah", 189, 115, 50)
            )
        )

        val set = BarDataSet(entries, "")
        val data = BarData(set)
        val legend = chart_province.legend
        val yAxis = chart_province.axisLeft
        val marker = CustomBarMarkerView(requireContext(), R.layout.custom_bar_marker)

        set.setDrawValues(false)
        set.setColors(
            requireContext().getColor(R.color.confirmed),
            requireContext().getColor(R.color.recovered),
            requireContext().getColor(R.color.deaths)
        )
        set.stackLabels = arrayOf("Confirmed", "Recovered", "Deaths")
        data.barWidth = 0.5f
        legend.textColor = requireContext().getColor(R.color.white)
        yAxis.mAxisMinimum = 0.0f
        yAxis.textColor = requireContext().getColor(R.color.white)

        chart_province.data = data
        chart_province.marker = marker
        chart_province.animateY(700)
        chart_province.setFitBars(true)
        chart_province.setVisibleXRangeMaximum(5f)
        chart_province.setDrawValueAboveBar(false)
        chart_province.invalidate()

    }
}
