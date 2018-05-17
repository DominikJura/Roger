package pl.jurassic.roger.feature.summary.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract.View
import pl.jurassic.roger.util.repository.Repository
import timber.log.Timber

class SummaryChartFragmentPresenter(
    private val view: View,
    private val repository: Repository,
    private val compositeDisposable: CompositeDisposable
) : Presenter {

    override fun initialize() {
        compositeDisposable.add(
            repository.getWorkTimeChartData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ view.setBarData(it) }, { Timber.e(it) }) //todo divide to recyler in feature
        )
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}