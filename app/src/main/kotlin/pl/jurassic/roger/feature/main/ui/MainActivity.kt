package pl.jurassic.roger.feature.main.ui

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pl.jurassic.roger.R
import pl.jurassic.roger.feature.common.ui.BaseActivity
import pl.jurassic.roger.feature.main.MainActivityContract.Presenter
import pl.jurassic.roger.feature.main.MainActivityContract.View
import pl.jurassic.roger.util.tools.EventHelper
import pl.jurassic.roger.util.tools.MainNavigationEvent
import javax.inject.Inject

class MainActivity : BaseActivity<Presenter>(true), View {

    @Inject
    lateinit var eventHelper: EventHelper

    override val layoutId: Int = R.layout.activity_main

    override fun showMainFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_root, MainFragment())
                .commit()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun navigationEvent(event: MainNavigationEvent) {
        eventHelper.handleEvent(event)
    }
}