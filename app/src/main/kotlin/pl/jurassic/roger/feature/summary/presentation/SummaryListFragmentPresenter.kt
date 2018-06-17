package pl.jurassic.roger.feature.summary.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.jurassic.roger.feature.summary.SummaryListFragmentContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryListFragmentContract.View
import pl.jurassic.roger.util.repository.Repository
import timber.log.Timber

class SummaryListFragmentPresenter(
    private val view: View,
    private val repository: Repository,
    private val compositeDisposable: CompositeDisposable
) : Presenter {

    override fun initialize() {
        compositeDisposable.add(repository.getWorkTimeList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { if(it.isEmpty()) view.showNoDataText() }
                .subscribe ({ view.setWorkTimeList(it) }, { Timber.e(it) })
        )
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}