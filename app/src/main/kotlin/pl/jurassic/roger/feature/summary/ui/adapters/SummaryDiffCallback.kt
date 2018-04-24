package pl.jurassic.roger.feature.summary.ui.adapters

import android.support.v7.util.DiffUtil
import pl.jurassic.roger.data.ui.SummaryWorkTime

class SummaryDiffCallback : DiffUtil.ItemCallback<SummaryWorkTime>() {

    override fun areItemsTheSame(oldItem: SummaryWorkTime, newItem: SummaryWorkTime): Boolean =
            oldItem.jobTime == newItem.jobTime

    override fun areContentsTheSame(oldItem: SummaryWorkTime, newItem: SummaryWorkTime): Boolean =
            oldItem.jobTime == newItem.jobTime
}