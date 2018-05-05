package pl.jurassic.roger.util.injection.modules.summary

import dagger.Module
import dagger.Provides
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract
import pl.jurassic.roger.feature.summary.presentation.SummaryChartFragmentPresenter
import pl.jurassic.roger.feature.summary.ui.SummaryChartFragment
import pl.jurassic.roger.util.injection.RuntimeScope

@Module
class SummaryChartFragmentModule {

    @Provides
    fun view(fragment: SummaryChartFragment): SummaryChartFragmentContract.View = fragment

    @RuntimeScope
    @Provides
    fun presenter(): SummaryChartFragmentContract.Presenter =
        SummaryChartFragmentPresenter()
}