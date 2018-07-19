package pl.jurassic.roger.util.injection.modules.summary

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract
import pl.jurassic.roger.feature.summary.presentation.SummaryChartFragmentPresenter
import pl.jurassic.roger.feature.summary.ui.SummaryChartFragment
import pl.jurassic.roger.feature.summary.ui.SummaryListFragment
import pl.jurassic.roger.feature.summary.ui.adapters.SummaryChartAdapter
import pl.jurassic.roger.feature.summary.ui.adapters.SummaryListAdapter
import pl.jurassic.roger.util.injection.RuntimeScope
import pl.jurassic.roger.util.repository.Repository
import pl.jurassic.roger.util.tools.DateFormatter

@Module
class SummaryChartFragmentModule {

    @Provides
    fun view(fragment: SummaryChartFragment): SummaryChartFragmentContract.View = fragment

    @Provides
    fun adapter(): SummaryChartAdapter = SummaryChartAdapter()

    @Provides
    fun recyclerLayoutManager(fragment: SummaryChartFragment): RecyclerView.LayoutManager =
            LinearLayoutManager(fragment.context, LinearLayoutManager.VERTICAL, false)

    @RuntimeScope
    @Provides
    fun presenter(
        view: SummaryChartFragmentContract.View,
        repository: Repository,
        compositeDisposable: CompositeDisposable
    ): SummaryChartFragmentContract.Presenter =
        SummaryChartFragmentPresenter(view, repository, compositeDisposable)
}