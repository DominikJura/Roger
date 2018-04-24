package pl.jurassic.roger.feature.main.navigation

import org.greenrobot.eventbus.EventBus
import pl.jurassic.roger.feature.main.MainFragmentContract
import pl.jurassic.roger.util.tools.MainNavigationEvent
import pl.jurassic.roger.util.tools.NavigationEvent

class MainFragmentRouter(
        private val eventBus: EventBus
) : MainFragmentContract.Router {

    override fun navigateToSummaryScreen() {
        eventBus.post(MainNavigationEvent.NAVIGATE_TO_SUMMARY)
    }
}
