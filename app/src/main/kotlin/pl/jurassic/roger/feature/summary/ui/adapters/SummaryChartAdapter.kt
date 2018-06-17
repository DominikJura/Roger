package pl.jurassic.roger.feature.summary.ui.adapters

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.jurassic.roger.R
import pl.jurassic.roger.data.ui.summary.SummaryChartData
import pl.jurassic.roger.feature.summary.ui.view.SummaryChartViewHolder

class SummaryChartAdapter : ListAdapter<SummaryChartData, SummaryChartViewHolder>(ChartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryChartViewHolder =
            SummaryChartViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.view_summary_chart_item, parent, false))

    override fun onBindViewHolder(holder: SummaryChartViewHolder, position: Int) {
        holder.setItem(getItem(position))
    }

    private class ChartDiffCallback : DiffUtil.ItemCallback<SummaryChartData>() {

        override fun areItemsTheSame(oldItem: SummaryChartData, newItem: SummaryChartData): Boolean =
                oldItem.weekIntervalText == newItem.weekIntervalText

        override fun areContentsTheSame(oldItem: SummaryChartData, newItem: SummaryChartData): Boolean =
                oldItem.weekIntervalText == newItem.weekIntervalText

    }
}