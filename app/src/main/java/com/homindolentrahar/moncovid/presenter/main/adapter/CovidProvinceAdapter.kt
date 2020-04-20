package com.homindolentrahar.moncovid.presenter.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.moncovid.data.pojo.CovidProvinceResult
import com.homindolentrahar.moncovid.databinding.ListItemProvinceBinding

class CovidProvinceAdapter :
    ListAdapter<CovidProvinceResult, CovidProvinceAdapter.CovidProvinceHolder>(
        CovidProvinceComparator
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidProvinceHolder =
        CovidProvinceHolder(
            ListItemProvinceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CovidProvinceHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CovidProvinceHolder(private val binding: ListItemProvinceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CovidProvinceResult) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    companion object CovidProvinceComparator : DiffUtil.ItemCallback<CovidProvinceResult>() {
        override fun areItemsTheSame(
            oldItem: CovidProvinceResult,
            newItem: CovidProvinceResult
        ): Boolean = oldItem.kodeProvinsi == newItem.kodeProvinsi

        override fun areContentsTheSame(
            oldItem: CovidProvinceResult,
            newItem: CovidProvinceResult
        ): Boolean = oldItem == newItem
    }
}