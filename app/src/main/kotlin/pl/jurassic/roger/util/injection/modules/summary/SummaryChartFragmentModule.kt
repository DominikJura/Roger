package pl.jurassic.roger.util.injection.modules.summary

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract
import pl.jurassic.roger.feature.summary.presentation.SummaryChartFragmentPresenter
import pl.jurassic.roger.feature.summary.ui.SummaryChartFragment
import pl.jurassic.roger.util.injection.RuntimeScope
import pl.jurassic.roger.util.repository.Repository
import pl.jurassic.roger.util.tools.DateFormatter

@Module
class SummaryChartFragmentModule {

    @Provides
    fun view(fragment: SummaryChartFragment): SummaryChartFragmentContract.View = fragment

    @RuntimeScope
    @Provides
    fun presenter(
        view: SummaryChartFragmentContract.View,
        repository: Repository,
        dateFormatter: DateFormatter,
        compositeDisposable: CompositeDisposable
    ): SummaryChartFragmentContract.Presenter =
        SummaryChartFragmentPresenter(view, repository, dateFormatter, compositeDisposable)
}