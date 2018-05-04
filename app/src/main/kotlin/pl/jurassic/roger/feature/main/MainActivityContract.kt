package pl.jurassic.roger.feature.main

import pl.jurassic.roger.feature.common.BaseContract

interface MainActivityContract {

    interface View {
        fun showMainFragment()
    }

    interface Router : MainFragmentContract.Router

    interface Presenter : BaseContract.Presenter {
        fun eventNavigationToSummary()
        fun onStatisticClicked()
    }
}