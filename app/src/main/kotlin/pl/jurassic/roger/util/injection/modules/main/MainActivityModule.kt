package pl.jurassic.roger.util.injection.modules.main

import dagger.Module
import dagger.Provides
import pl.jurassic.roger.feature.main.MainActivityContract
import pl.jurassic.roger.feature.main.navigation.MainActivityEventHelper
import pl.jurassic.roger.feature.main.navigation.MainActivityRouter
import pl.jurassic.roger.feature.main.presentation.MainActivityPresenter
import pl.jurassic.roger.feature.main.ui.MainActivity
import pl.jurassic.roger.util.injection.RuntimeScope
import pl.jurassic.roger.util.tools.EventHelper

@Module
class MainActivityModule {

    @Provides
    fun view(activity: MainActivity): MainActivityContract.View = activity

    @Provides
    fun router(activity: MainActivity): MainActivityContract.Router =
            MainActivityRouter(activity)

    @Provides
    fun eventHelper(presenter: MainActivityContract.Presenter): EventHelper =
            MainActivityEventHelper(presenter)

    @RuntimeScope
    @Provides
    fun presenter(
        view: MainActivityContract.View,
        router: MainActivityContract.Router
    ): MainActivityContract.Presenter =
            MainActivityPresenter(view, router)
}