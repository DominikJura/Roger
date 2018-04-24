package pl.jurassic.roger.feature.summary

import pl.jurassic.roger.data.ui.SummaryWorkTime
import pl.jurassic.roger.feature.common.BaseContract

interface SummaryFragmentContract {

    interface View {
        fun setWorkTimeList(summaryWorkList: List<SummaryWorkTime>)
    }

    interface Router

    interface Presenter : BaseContract.Presenter
}