package pl.jurassic.roger.feature.summary.ui.adapters

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.jurassic.roger.R
import pl.jurassic.roger.data.ui.SummaryWorkTime
import pl.jurassic.roger.feature.summary.ui.view.SummaryViewHolder

class SummaryAdapter : ListAdapter<SummaryWorkTime, SummaryViewHolder>(SummaryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder =
            SummaryViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.view_summary_item, parent, false))

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        when(position % 2) {
            0 -> holder.setBackground(R.color.summary_item_background)
            else -> holder.setBackground(R.color.white)
        }
        holder.setItem(getItem(position))
    }
}