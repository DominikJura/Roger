package pl.jurassic.roger.feature.summary

import pl.jurassic.roger.data.ui.summary.SummaryChartData
import pl.jurassic.roger.feature.common.BaseContract

interface SummaryChartFragmentContract {

    interface View {
        fun showNoDataText()
        fun setChartListData(chartListData: List<SummaryChartData>)
    }

    interface Router

    interface Presenter : BaseContract.Presenter
}