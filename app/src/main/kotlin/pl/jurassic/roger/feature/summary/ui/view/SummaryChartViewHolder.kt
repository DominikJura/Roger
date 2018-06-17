package pl.jurassic.roger.feature.summary.ui.view

import android.support.v7.widget.RecyclerView
import android.view.View
import pl.jurassic.roger.data.ui.BreakBarData
import pl.jurassic.roger.data.ui.summary.SummaryChartData

import kotlinx.android.synthetic.main.view_summary_chart_item.view.summary_chart as chartView
import kotlinx.android.synthetic.main.view_summary_chart_item.view.summary_weeks_interval as weekIntervalTextView

class SummaryChartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun setItem(chartData: SummaryChartData) = with(chartData) {
        setWeekIntervalText(weekIntervalText)
        setBarData(barDataList)
    }

    private fun setBarData(barDataList: List<BreakBarData>) {
        itemView.chartView.setBarData(barDataList)
    }

    private fun setWeekIntervalText(weekIntervalText: String) {
        itemView.weekIntervalTextView.text = weekIntervalText
    }
}