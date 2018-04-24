package pl.jurassic.roger.feature.summary.presentation

import pl.jurassic.roger.feature.summary.SummaryActivityContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryActivityContract.View

class SummaryActivityPresenter(private val view: View) : Presenter {

    override fun initialize() {
        view.showSummaryFragment()
    }

    override fun clear() = Unit
}