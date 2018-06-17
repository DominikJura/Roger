package pl.jurassic.roger.feature.summary.ui.adapters

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.jurassic.roger.R
import pl.jurassic.roger.data.ui.SummaryWorkTime
import pl.jurassic.roger.feature.summary.ui.view.SummaryListViewHolder

class SummaryListAdapter : ListAdapter<SummaryWorkTime, SummaryListViewHolder>(ListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryListViewHolder =
            SummaryListViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.view_summary_list_item, parent, false))

    override fun onBindViewHolder(holder: SummaryListViewHolder, position: Int) = with(holder) {
        when (position % 2) {
            0 -> setBackground(R.color.summary_item_background)
            else -> setBackground(R.color.white)
        }
        setItem(getItem(position))
    }

    private class ListDiffCallback : DiffUtil.ItemCallback<SummaryWorkTime>() {

        override fun areItemsTheSame(oldItem: SummaryWorkTime, newItem: SummaryWorkTime): Boolean =
                oldItem.jobTime == newItem.jobTime

        override fun areContentsTheSame(oldItem: SummaryWorkTime, newItem: SummaryWorkTime): Boolean =
                oldItem.jobTime == newItem.jobTime
    }
}