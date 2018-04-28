package pl.jurassic.roger.feature.summary.ui.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import pl.jurassic.roger.R
import pl.jurassic.roger.data.ui.SummaryWorkTime
import pl.jurassic.roger.feature.common.ui.view.BreakItemView
import pl.jurassic.roger.util.timer.BreakType
import kotlinx.android.synthetic.main.view_summary_item.view.summary_item_break_time as breakTimeTimeView
import kotlinx.android.synthetic.main.view_summary_item.view.summary_item_break_view_container as containerLinearLayout
import kotlinx.android.synthetic.main.view_summary_item.view.summary_item_date as dateTextView
import kotlinx.android.synthetic.main.view_summary_item.view.summary_item_work_time as workTimeView

class SummaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun setItem(summaryWorkTime: SummaryWorkTime) = with(itemView) {
        dateTextView.text = summaryWorkTime.dateTime
        workTimeView.dateTimeText = summaryWorkTime.jobTime
        breakTimeTimeView.dateTimeText = summaryWorkTime.breakTotalTime

        summaryWorkTime.breakTimeList.forEach {
            addBreakView(it.breakType, it.breakTime)
        }
    }

    private fun addBreakView(breakType: BreakType, totalTime: String) {
        val tmp = BreakItemView(itemView.context)

        val res = when (breakType) {
            BreakType.LUNCH -> R.drawable.break_lunch_background_selector
            BreakType.SMOKING -> R.drawable.break_smoking_background_selector
            BreakType.OTHER -> R.drawable.break_other_background_selector
        }

        tmp.breakBackgroundResource = res
        tmp.breakImageDrawable = R.drawable.ic_arrow_back
        tmp.breakImageColor = R.color.break_item_smoking_image_tint_inactive

        tmp.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        itemView.containerLinearLayout.addView(tmp)
    }
}