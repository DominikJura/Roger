package pl.jurassic.roger.util.injection.modules.summary

import dagger.Module
import dagger.Provides
import pl.jurassic.roger.feature.summary.SummaryActivityContract
import pl.jurassic.roger.feature.summary.presentation.SummaryActivityPresenter
import pl.jurassic.roger.feature.summary.ui.SummaryActivity
import pl.jurassic.roger.util.injection.RuntimeScope

@Module
class SummaryActivityModule {

    @Provides
    fun view(activity: SummaryActivity): SummaryActivityContract.View = activity

    @RuntimeScope
    @Provides
    fun presenter(view: SummaryActivityContract.View): SummaryActivityContract.Presenter =
            SummaryActivityPresenter(view)
}