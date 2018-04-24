package pl.jurassic.roger.feature.main.presentation

import pl.jurassic.roger.feature.main.MainActivityContract.Router
import pl.jurassic.roger.feature.main.MainActivityContract.Presenter
import pl.jurassic.roger.feature.main.MainActivityContract.View

class MainActivityPresenter(private val view: View, private val router: Router) : Presenter {

    override fun initialize() {
        view.showMainFragment()
    }

    override fun eventNavigationToSummary() {
        router.navigateToSummaryScreen()
    }

    override fun clear() = Unit
}