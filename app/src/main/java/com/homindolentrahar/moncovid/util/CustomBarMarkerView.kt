package com.homindolentrahar.moncovid.util

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.homindolentrahar.moncovid.model.pojo.CovidProvinceResult
import kotlinx.android.synthetic.main.custom_bar_marker.view.*

class CustomBarMarkerView(context: Context, layoutRes: Int) : MarkerView(context, layoutRes) {

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val data = e?.data as CovidProvinceResult
        tv_province.text = data.provinsi
        tv_confirmed.text = data.positif.toString()
        tv_recovered.text = data.sembuh.toString()
        tv_deaths.text = data.meninggal.toString()
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -measuredHeight.toFloat())
    }
}