package pl.jurassic.roger.util.injection.modules.summary

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import pl.jurassic.roger.feature.summary.SummaryListFragmentContract
import pl.jurassic.roger.feature.summary.presentation.SummaryListFragmentPresenter
import pl.jurassic.roger.feature.summary.ui.SummaryListFragment
import pl.jurassic.roger.feature.summary.ui.adapters.SummaryListAdapter
import pl.jurassic.roger.util.injection.RuntimeScope
import pl.jurassic.roger.util.repository.Repository

@Module
class SummaryListFragmentModule {

    @Provides
    fun view(fragment: SummaryListFragment): SummaryListFragmentContract.View = fragment

    @Provides
    fun adapter(): SummaryListAdapter = SummaryListAdapter()

    @Provides
    fun recyclerLayoutManager(fragment: SummaryListFragment): RecyclerView.LayoutManager =
            LinearLayoutManager(fragment.context, LinearLayoutManager.VERTICAL, false)

    @RuntimeScope
    @Provides
    fun presenter(
        view: SummaryListFragmentContract.View,
        repository: Repository,
        compositeDisposable: CompositeDisposable
    ): SummaryListFragmentContract.Presenter =
            SummaryListFragmentPresenter(view, repository, compositeDisposable)
}