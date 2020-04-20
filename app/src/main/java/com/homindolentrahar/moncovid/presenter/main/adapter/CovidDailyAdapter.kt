package com.homindolentrahar.moncovid.presenter.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.moncovid.data.pojo.CovidDailyResult
import com.homindolentrahar.moncovid.databinding.ListItemDailyBinding
import com.homindolentrahar.moncovid.util.Constant

class CovidDailyAdapter :
    ListAdapter<CovidDailyResult, CovidDailyAdapter.CovidDailyHolder>(CovidDailyComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidDailyHolder =
        CovidDailyHolder(
            ListItemDailyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CovidDailyHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CovidDailyHolder(private val binding: ListItemDailyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CovidDailyResult) {
            val date = "${Constant.getDateFromTimestamp(item.tanggal)}  (Day ${item.hariKe})"
            binding.tvDate.text = date
            binding.tvConfirmed.text = item.kasusKumulatif.toString()
            binding.tvRecovered.text = item.sembuh.toString()
            binding.tvDeaths.text = item.meninggal.toString()

            if (item.kasusBaruPerHari > 0) {
                val progress = "+" + item.kasusBaruPerHari.toString()
                binding.tvConfirmedProgress.text = progress
            }
            if (item.sembuhPerHari > 0) {
                val progress = "+" + item.sembuhPerHari.toString()
                binding.tvRecoveredProgress.text = progress
            }
            if (item.meninggalPerHari > 0) {
                val progress = "+" + item.meninggalPerHari.toString()
                binding.tvDeathsProgress.text = progress
            }
        }
    }

    companion object CovidDailyComparator : DiffUtil.ItemCallback<CovidDailyResult>() {
        override fun areItemsTheSame(
            oldItem: CovidDailyResult,
            newItem: CovidDailyResult
        ): Boolean = oldItem.hariKe == newItem.hariKe

        override fun areContentsTheSame(
            oldItem: CovidDailyResult,
            newItem: CovidDailyResult
        ): Boolean = oldItem == newItem
    }
}