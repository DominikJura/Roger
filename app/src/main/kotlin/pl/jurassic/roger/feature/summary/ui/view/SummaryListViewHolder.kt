package pl.jurassic.roger.feature.summary.ui.view

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.View
import pl.jurassic.roger.R
import pl.jurassic.roger.data.ui.SummaryWorkTime
import pl.jurassic.roger.feature.common.ui.view.BreakItemView
import pl.jurassic.roger.getColor
import pl.jurassic.roger.getDimen
import pl.jurassic.roger.data.BreakType
import kotlinx.android.synthetic.main.view_summary_list_item.view.summary_item_break_time as breakTimeTimeView
import kotlinx.android.synthetic.main.view_summary_list_item.view.summary_item_break_view_container as containerLinearLayout
import kotlinx.android.synthetic.main.view_summary_list_item.view.summary_item_date as dateTextView
import kotlinx.android.synthetic.main.view_summary_list_item.view.summary_item_work_time as workTimeView

class SummaryListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun setItem(summaryWorkTime: SummaryWorkTime) = with(itemView) {
        dateTextView.text = summaryWorkTime.dateTime
        workTimeView.dateTimeText = summaryWorkTime.jobTime
        breakTimeTimeView.dateTimeText = summaryWorkTime.breakTotalTime

        summaryWorkTime.breakTimeList.forEach {
            addBreakView(it.breakType, it.breakTime)
        }
    }

    private fun addBreakView(breakType: BreakType, totalTime: String) {
        val breakView = when (breakType) {
            BreakType.LUNCH -> getBreakView(
                    R.drawable.break_lunch_background_selector,
                    R.color.break_lunch,
                    R.drawable.ic_lunch
            )

            BreakType.SMOKING -> getBreakView(
                    R.drawable.break_smoking_background_selector,
                    R.color.break_smoking,
                    R.drawable.ic_smoke
            )

            BreakType.OTHER -> getBreakView(
                    R.drawable.break_other_background_selector,
                    R.color.break_other,
                    R.drawable.ic_other
            )
        }

        breakView.breakTimeText = totalTime

        breakView.setPadding(
                0,
                getDimen(R.dimen.summary_item_break_element_margin_top),
                getDimen(R.dimen.summary_item_break_element_margin_right),
                getDimen(R.dimen.summary_item_break_element_margin_bottom))

        itemView.containerLinearLayout.addView(breakView)
    }

    private fun getBreakView(
        @DrawableRes backgroundResource: Int,
        @ColorRes color: Int,
        @DrawableRes imageDrawable: Int
    ): BreakItemView = BreakItemView(itemView.context).apply {
        breakBackgroundResource = backgroundResource
        breakImageColor = color
        breakTimeTextColor = getColor(color)
        breakImageDrawable = imageDrawable
    }

    fun setBackground(@ColorRes color: Int) {
        itemView.setBackgroundColor(getColor(color))
    }
}