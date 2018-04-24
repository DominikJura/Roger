package pl.jurassic.roger.util.timer

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import pl.jurassic.roger.util.tools.JobTimer
import pl.jurassic.roger.util.tools.JobTimerImpl
import timber.log.Timber
import javax.inject.Inject

typealias TimeUpdateCallback = (time: Long) -> Unit

class TimerService : Service() {

    @Inject
    lateinit var jobTimer: JobTimer

    @Inject
    lateinit var configuration: TimerConfiguration

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var jobTimeDisposable: Disposable
    private lateinit var breakTimeDisposable: Disposable

    var timeUpdateCallback: TimeUpdateCallback? = null
    var breakUpdateCallback: TimeUpdateCallback? = null


    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onBind(intent: Intent?): IBinder =
        TimerServiceBinder(this)

    fun startBreakTimer(breakType: BreakType) {
        configuration.breakTypeStartTime[breakType] = DateTime.now().millis

        breakTimeDisposable = jobTimer.timerObservable(getBreakStartTime(breakType))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it + getBreakTotalTimeSum() }
                .subscribe ({ breakUpdateCallback?.invoke(it) }, { Timber.e(it)})

        compositeDisposable.add(breakTimeDisposable)
    }

    private fun getBreakTotalTime(breakType: BreakType) =
            configuration.breakTypeTotalTime[breakType] ?: 0L

    private fun getBreakStartTime(breakType: BreakType) =
            configuration.breakTypeStartTime[breakType] ?: 0L

    private fun getBreakTotalTimeSum() =
            configuration.breakTypeTotalTime.values.sum()

    fun pauseBreakTimer(breakType: BreakType) {
        compositeDisposable.remove(breakTimeDisposable)

        val totalBreakTime = DateTime.now().millis - getBreakStartTime(breakType)
        configuration.breakTypeTotalTime[breakType] = getBreakTotalTime(breakType) + totalBreakTime
    }

    fun startJobTimer() {
        configuration.isRunning = true
        configuration.startTime = DateTime.now().millis
        jobTimeDisposable = jobTimer.timerObservable(
                configuration.startTime,
                configuration.jobTimeThatAlreadyPass
        )
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ timeUpdateCallback?.invoke(it) }, { Timber.e(it)})

        compositeDisposable.add(jobTimeDisposable)
    }

    fun pauseJobTimer() {
        compositeDisposable.remove(jobTimeDisposable)

        configuration.isRunning = false
        configuration.jobTimeThatAlreadyPass += DateTime.now().millis - configuration.startTime
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}

class TimerServiceBinder(val service: TimerService) : Binder()