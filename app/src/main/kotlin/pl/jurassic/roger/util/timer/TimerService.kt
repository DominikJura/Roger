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

    var timeUpdateCallback: TimeUpdateCallback? = null
    var breakUpdateCallback: ((breakType: BreakType, time: Long) -> Unit)? = null

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onBind(intent: Intent?): IBinder =
        TimerServiceBinder(this)

    fun startBreakTimer(breakType: BreakType) {
        if(!configuration.isRunning) {
            startJobTimer()
        }

        val breakStartTimestamp = DateTime.now().millis

        configuration.breakTimesList.add(BreakTime(breakType, breakStartTimestamp, breakStartTimestamp))

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
        configuration.isRunning = true
        if(!configuration.initialize) {
            configuration.startTime = DateTime.now().millis
            configuration.initialize = true
        }
        jobTimeDisposable = jobTimer.timerObservable(configuration.startTime)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ timeUpdateCallback?.invoke(it) }, { Timber.e(it) })

        compositeDisposable.add(jobTimeDisposable)
    }

    fun pauseJobTimer() {
        compositeDisposable.remove(jobTimeDisposable)

        configuration.isRunning = false
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}

class TimerServiceBinder(val service: TimerService) : Binder()