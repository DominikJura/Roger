package pl.jurassic.roger.feature.summary

import pl.jurassic.roger.data.ui.BreakBarData
import pl.jurassic.roger.feature.common.BaseContract

interface SummaryChartFragmentContract {

    interface View {
        fun setBarData(barDataList: List<BreakBarData>)
    }

    interface Router

    interface Presenter : BaseContract.Presenter
}