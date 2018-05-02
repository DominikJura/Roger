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
import pl.jurassic.roger.data.BreakTime
import pl.jurassic.roger.util.tools.JobTimer
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

    var timeThatPass = 0L //todo refactor

    var timeUpdateCallback: TimeUpdateCallback? = null
    var breakUpdateCallback: ((breakType: BreakType, time: Long) -> Unit)? = null

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onBind(intent: Intent?): IBinder =
        TimerServiceBinder(this)

    fun startBreakTimer(breakType: BreakType) {
        val breakStartTimestamp = DateTime.now().millis

        configuration.breakTimesList.add(BreakTime(
            breakType,
            breakStartTimestamp,
            timeThatPass,
            breakStartTimestamp
        ))

        breakTimeDisposable = jobTimer.timerObservable(breakStartTimestamp)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ breakUpdateCallback?.invoke(breakType, it) }, { Timber.e(it) })

        compositeDisposable.add(breakTimeDisposable)
    }

    fun pauseBreakTimer() {
        compositeDisposable.remove(breakTimeDisposable)

        configuration.breakTimesList.last().stopTimestamp = DateTime.now().millis
    }

    fun startJobTimer() {
        if(!configuration.isRunning) {

            configuration.isRunning = true

            configuration.startJobTime = DateTime.now().millis

            jobTimeDisposable = jobTimer.timerObservable(configuration.startJobTime, configuration.totalJobTimeThatPass)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { timeThatPass = it }
                .subscribe({ timeUpdateCallback?.invoke(it) }, { Timber.e(it) })

            compositeDisposable.add(jobTimeDisposable)
        }
    }

    fun pauseJobTimer() {
        compositeDisposable.remove(jobTimeDisposable)

        configuration.totalJobTimeThatPass += DateTime.now().millis - configuration.startJobTime

        configuration.isRunning = false
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}

class TimerServiceBinder(val service: TimerService) : Binder()