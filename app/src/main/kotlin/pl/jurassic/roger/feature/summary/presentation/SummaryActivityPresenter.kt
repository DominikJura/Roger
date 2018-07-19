package pl.jurassic.roger.feature.summary.presentation

import pl.jurassic.roger.feature.summary.SummaryActivityContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryActivityContract.View

class SummaryActivityPresenter(private val view: View) : Presenter {

    override fun initialize() = Unit

    override fun clear() = Unit

    override fun onBackPressed() {
        view.navigateBack()
    }
}