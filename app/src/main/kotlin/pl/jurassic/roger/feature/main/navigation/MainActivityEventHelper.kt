package pl.jurassic.roger.feature.main.navigation

import pl.jurassic.roger.feature.main.MainActivityContract
import pl.jurassic.roger.util.tools.EventHelper
import pl.jurassic.roger.util.tools.MainNavigationEvent
import pl.jurassic.roger.util.tools.NavigationEvent

class MainActivityEventHelper(
    private val presenter: MainActivityContract.Presenter
) : EventHelper {

    override fun handleEvent(event: NavigationEvent) {
        when (event) {
            MainNavigationEvent.NAVIGATE_TO_SUMMARY -> presenter.eventNavigationToSummary()
        }
    }
}