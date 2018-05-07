package pl.jurassic.roger.feature.summary.ui

import pl.jurassic.roger.R
import pl.jurassic.roger.data.ui.BreakBarData
import pl.jurassic.roger.feature.common.ui.BaseFragment
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract.View

import kotlinx.android.synthetic.main.fragment_summary_chart.summary_chart as chartView

class SummaryChartFragment : BaseFragment<Presenter>(), View {

    override val layoutId: Int = R.layout.fragment_summary_chart

    override fun setBarData(barDataList: List<BreakBarData>) {
        chartView.setBarData(barDataList)
    }
}