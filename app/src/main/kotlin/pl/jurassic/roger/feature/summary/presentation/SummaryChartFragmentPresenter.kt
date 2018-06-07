package pl.jurassic.roger.feature.summary.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract.View
import pl.jurassic.roger.util.repository.Repository
import pl.jurassic.roger.util.tools.DateFormatter
import timber.log.Timber

class SummaryChartFragmentPresenter(
    private val view: View,
    private val repository: Repository,
    private val dateFormatter: DateFormatter,
    private val compositeDisposable: CompositeDisposable
) : Presenter {

    override fun initialize() {
        compositeDisposable.add(
                repository.getWorkTimeChartData()
                        .filter { it.isNotEmpty() }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            val firstDate = it.first().dateTime
                            view.setWeekIntervalText(dateFormatter.parseWeekIntervalDate(firstDate))
                            view.setBarData(it)
                        }, { Timber.e(it) }) //todo divide to recyler in feature
        )
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}