package pl.jurassic.roger.util.injection.modules.summary

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import pl.jurassic.roger.feature.summary.SummaryFragmentContract
import pl.jurassic.roger.feature.summary.presentation.SummaryFragmentPresenter
import pl.jurassic.roger.feature.summary.ui.SummaryFragment
import pl.jurassic.roger.feature.summary.ui.adapters.SummaryAdapter
import pl.jurassic.roger.util.injection.RuntimeScope
import pl.jurassic.roger.util.repository.Repository

@Module
class SummaryFragmentModule {

    @Provides
    fun view(fragment: SummaryFragment): SummaryFragmentContract.View = fragment

    @Provides
    fun adapter(): SummaryAdapter = SummaryAdapter()

    @Provides
    fun recyclerLayoutManager(fragment: SummaryFragment): RecyclerView.LayoutManager =
            LinearLayoutManager(fragment.context, LinearLayoutManager.VERTICAL, false)

    @RuntimeScope
    @Provides
    fun presenter(
        view: SummaryFragmentContract.View,
        repository: Repository,
        compositeDisposable: CompositeDisposable
    ): SummaryFragmentContract.Presenter =
            SummaryFragmentPresenter(view, repository, compositeDisposable)
}