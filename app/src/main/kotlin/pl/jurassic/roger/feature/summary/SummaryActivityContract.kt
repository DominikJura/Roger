package pl.jurassic.roger.feature.summary

import pl.jurassic.roger.feature.common.BaseContract

interface SummaryActivityContract {

    interface View {
        fun navigateBack()
    }

    interface Router

    interface Presenter : BaseContract.Presenter {
        fun onBackPressed()
    }
}