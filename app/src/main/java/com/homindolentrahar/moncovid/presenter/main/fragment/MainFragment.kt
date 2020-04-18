package com.homindolentrahar.moncovid.presenter.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.homindolentrahar.moncovid.R
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private val entries = mutableListOf<PieEntry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Refresh Data
        refresh()
//        Chart setup
        initChart()
    }

    private fun refresh(){
        swipe_refresh.setOnRefreshListener {
            Toast.makeText(requireContext(),"Refreshing",Toast.LENGTH_SHORT).show()
            swipe_refresh.isRefreshing = false
        }
    }

    private fun initChart() {
        entries.add(PieEntry(55f, "Confirmed"))
        entries.add(PieEntry(30f, "Recovered"))
        entries.add(PieEntry(15f, "Deaths"))

        val set = PieDataSet(entries, "Overview")
        val data = PieData(set)

        set.setDrawValues(false)
        set.setColors(
            requireContext().getColor(R.color.confirmed),
            requireContext().getColor(R.color.recovered),
            requireContext().getColor(R.color.deaths)
        )

        chart_overview.data = data
        chart_overview.animateY(700)
        chart_overview.setDrawSliceText(false)
        chart_overview.setDrawEntryLabels(false)
        chart_overview.holeRadius = 70f
        chart_overview.setHoleColor(requireContext().getColor(R.color.primaryDark))
        chart_overview.description.isEnabled = false
        chart_overview.legend.isEnabled = false
        chart_overview.invalidate()
    }
}
