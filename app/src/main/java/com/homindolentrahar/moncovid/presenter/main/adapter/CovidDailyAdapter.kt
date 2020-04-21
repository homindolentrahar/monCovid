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
            val progressConfirmed = "+" + item.kasusBaruPerHari.toString()
            val progressRecovered = "+" + item.sembuhPerHari.toString()
            val progressDeaths = "+" + item.meninggalPerHari.toString()

            binding.tvDate.text = date
            binding.tvConfirmed.text = item.kasusKumulatif.toString()
            binding.tvRecovered.text = item.sembuh.toString()
            binding.tvDeaths.text = item.meninggal.toString()
            binding.tvConfirmedProgress.text = progressConfirmed
            binding.tvRecoveredProgress.text = progressRecovered
            binding.tvDeathsProgress.text = progressDeaths
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