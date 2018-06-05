package pl.jurassic.roger.feature.main.presentation

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.Subject
import org.joda.time.DateTime
import pl.jurassic.roger.R
import pl.jurassic.roger.data.WorkTime
import pl.jurassic.roger.data.ui.BreakProgressAngle
import pl.jurassic.roger.data.ui.ProgressAngles
import pl.jurassic.roger.data.ui.ProgressHourIndicator
import pl.jurassic.roger.feature.main.MainFragmentContract.Presenter
import pl.jurassic.roger.feature.main.MainFragmentContract.Router
import pl.jurassic.roger.feature.main.MainFragmentContract.View
import pl.jurassic.roger.sumByLong
import pl.jurassic.roger.util.repository.Repository
import pl.jurassic.roger.data.BreakType
import pl.jurassic.roger.util.tools.DateFormatter
import timber.log.Timber

class MainFragmentPresenter(
    private val view: View,
    private val router: Router,
    private val dateFormatter: DateFormatter,
    private val repository: Repository,
    private val jobTimeSubject: Subject<Long>,
    private val breakTimeSubject: Subject<Long>,
    private val compositeDisposable: CompositeDisposable
) : Presenter {

    companion object {
        private const val CIRCLE_FULL_ANGLE = 360f
        private const val WORK_TIME_HOURS = 60f //todo take from share-prefs
        private const val MINUTES_IN_HOUR = 60f * WORK_TIME_HOURS
        private const val SECONDS_IN_MINUTS = 60f
        private const val MILLISECONDS_IN_SECONDS = 1000f
    }

    private var WORK_TIME = 8 //TODo refactor

    private val configuration by lazy { view.timerService.configuration }

    override fun initialize() {
        view.setProgressAngles(getProgressAngles(0f, emptyList()))

        compositeDisposable.add(
                Observable.combineLatest(
                        jobTimeSubject
                                .doOnNext { WORK_TIME += (it / (WORK_TIME * MINUTES_IN_HOUR * MILLISECONDS_IN_SECONDS)).toInt() }
                                .map { countProgressAngle(it) },
                        breakTimeSubject.map { transformToProgressAngleList(it) },
                        BiFunction<Float, List<BreakProgressAngle>, ProgressAngles>
                        { jobProgress, breakProgresses -> getProgressAngles(jobProgress, breakProgresses) }
                )
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ view.setProgressAngles(it) }, { Timber.e(it) })
        )
    }

    private fun getProgressAngles(jobProgress: Float, breakProgressAngles: List<BreakProgressAngle>): ProgressAngles {
        val indicatorList = MutableList(WORK_TIME) {
            val indicatorStartingAngle = (CIRCLE_FULL_ANGLE / WORK_TIME) * it
            val color = when (jobProgress > indicatorStartingAngle) {
                true -> R.color.pale_grey
                else -> R.color.lightish_blue
            }
            ProgressHourIndicator(indicatorStartingAngle, color)
        }

        return ProgressAngles(jobProgress, indicatorList, breakProgressAngles)
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun onServiceConnect() {
        when (configuration.isRunning) {
            true -> view.activeJobButton()
            false -> view.deactivateJobButton()
        }

        when(configuration.activeBreakType) {
            BreakType.SMOKING -> view.activeSmokingButton()
            BreakType.LUNCH -> view.activeLunchButton()
            BreakType.OTHER -> view.activeOtherButton()
        }

        breakTimeSubject.onNext(0) //Todo refactor
    }

    override fun onTimerButtonClicked() {
        when (configuration.isRunning) {
            true -> pauseTimer()
            false -> startTimer()
        }
    }

    private fun startTimer() = with(view) {
        timerService.startJobTimer()
        activeJobButton()
        hideSaveButton()
    }

    private fun pauseTimer() = with(view) {
        timerService.pauseJobTimer()
        deactivateAllButtons()
        showSaveButton()
    }

    private fun deactivateAllButtons() = with(view) {
        deactivateJobButton()
        deactivateSmokingButton()
        deactivateLunchButton()
        deactivateOtherButton()
    }

    override fun onJobTimeReceive(time: Long) {
        jobTimeSubject.onNext(time)
        view.setJobTime(dateFormatter.parseTime(time))
    }

    private fun countProgressAngle(time: Long): Float =
            CIRCLE_FULL_ANGLE * time / (WORK_TIME * MINUTES_IN_HOUR * MILLISECONDS_IN_SECONDS) //todo add SECONDS_IN_MINUTS

    override fun onBreakTimeReceive(breakType: BreakType, time: Long) {
        val breakTime = dateFormatter.parseTime(getBreakTime(breakType) + time)
        when (breakType) {
            BreakType.LUNCH -> view.setLunchTimeText(breakTime)
            BreakType.SMOKING -> view.setSmokingTimeText(breakTime)
            BreakType.OTHER -> view.setOtherTimeText(breakTime)
        }
        breakTimeSubject.onNext(time)
        view.setBreakTotalTime(dateFormatter.parseTime(getBreakTotalTime() + time))
    }

    private fun getBreakTotalTime(): Long =
            configuration.breakTimesList.sumByLong { (it.stopTimestamp - it.startTimestamp) }

    private fun getBreakTime(breakType: BreakType): Long =
            configuration.breakTimesList
                    .filter { it.breakType == breakType }
                    .sumByLong { (it.stopTimestamp - it.startTimestamp) }

    private fun transformToProgressAngleList(time: Long): List<BreakProgressAngle> =
            configuration.breakTimesList
                    .map {
                        val startAngle = countProgressAngle(it.jobTimeThatPass)
                        val sweepAngle = countProgressAngle(it.stopTimestamp - it.startTimestamp)
                        BreakProgressAngle(startAngle, sweepAngle, it.breakType.breakColorRes)
                    }
                    .also { if (it.isNotEmpty()) it.last().sweepAngle += countProgressAngle(time) }

    override fun onSmokingItemClicked(isSelected: Boolean) = with(view) {
        when (isSelected) {
            true -> {
                pauseBreakTimer()
                deactivateSmokingButton()
            }
            false -> {
                startBreakTimer(BreakType.SMOKING)
                startTimer()

                deactivateOtherButton()
                deactivateLunchButton()
                activeSmokingButton()
            }
        }
    }

    private fun pauseBreakTimer() = with(view) {
        configuration.activeBreakType?.let { timerService.pauseBreakTimer() }
        configuration.activeBreakType = null
    }

    private fun startBreakTimer(breakType: BreakType) = with(view) {
        configuration.activeBreakType?.let { timerService.pauseBreakTimer() }
        configuration.activeBreakType = breakType
        timerService.startBreakTimer(breakType)
    }

    override fun onLunchItemClicked(isSelected: Boolean) = with(view) {
        when (isSelected) {
            true -> {
                pauseBreakTimer()
                deactivateLunchButton()
            }
            false -> {
                startBreakTimer(BreakType.LUNCH)
                startTimer()

                deactivateOtherButton()
                deactivateSmokingButton()
                activeLunchButton()
            }
        }
    }

    override fun onOtherItemClicked(isSelected: Boolean) = with(view) {
        when (isSelected) {
            true -> {
                pauseBreakTimer()
                deactivateOtherButton()
            }
            false -> {
                startBreakTimer(BreakType.OTHER)
                startTimer()

                deactivateSmokingButton()
                deactivateLunchButton()
                activeOtherButton()
            }
        }
    }

    override fun onNotificationPauseClicked() = with(view) {
        deactivateAllButtons()
        showSaveButton()
    }

    override fun onNotificationResumeClicked() = with(view) {
        activeJobButton()
        hideSaveButton()
    }

    override fun onSaveClicked() = with(configuration) {
        val workTime = WorkTime(startJobTime, DateTime.now(), totalJobTimeThatPass, breakTimesList)
        //TODO Save break time with 0 value if it was not started
        repository.saveWorkTime(workTime)

        router.navigateToSummaryScreen()

        clearConfiguration()
        clearView()
    }

    private fun clearConfiguration() = with(configuration) {
        isRunning = false
        startJobTime = DateTime.now()
        timeThatPass = 0L
        totalJobTimeThatPass = 0L
        breakTimesList = arrayListOf()
    }

    private fun clearView() = with(view) {
        val zeroTime = dateFormatter.parseTime(0L)

        setJobTime(zeroTime)
        setBreakTotalTime(zeroTime)
        setLunchTimeText(zeroTime)
        setSmokingTimeText(zeroTime)
        setOtherTimeText(zeroTime)
        setProgressAngles(getProgressAngles(0f, emptyList()))
        hideSaveButton()

        jobTimeSubject.onNext(0) // TODO refactor
        breakTimeSubject.onNext(0) // TODO refactor
    }
}