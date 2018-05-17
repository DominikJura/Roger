package pl.jurassic.roger.util.timer

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import pl.jurassic.roger.R
import pl.jurassic.roger.data.BreakTime
import pl.jurassic.roger.util.tools.JobTimer
import timber.log.Timber
import java.time.Clock
import javax.inject.Inject

typealias TimeUpdateCallback = (time: Long) -> Unit

class TimerService : Service() {

    companion object {
        private const val NOTIFICATION_ID = 5324
    }

    @Inject
    lateinit var jobTimer: JobTimer

    @Inject
    lateinit var configuration: TimerConfiguration

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var customNotificationLayout: RemoteViews

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

            startForeground(NOTIFICATION_ID, notificationBuilder.build())
            startChronometer(DateTime.now().millis)

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

        stopForeground(true)

        configuration.totalJobTimeThatPass += DateTime.now().millis - configuration.startJobTime

        pauseChronometer(configuration.totalJobTimeThatPass)

        configuration.isRunning = false
    }

    private fun updateNotificationLayout() {
        notificationBuilder.setCustomContentView(customNotificationLayout)
        notificationBuilder.setCustomBigContentView(customNotificationLayout)
        notificationBuilder.setCustomHeadsUpContentView(customNotificationLayout)
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun showStartNotificationButton() {
        customNotificationLayout.setImageViewResource(R.id.notification_button, R.drawable.ic_play)
//        customNotificationLayout.setOnClickPendingIntent(R.id.notification_button, resumeNotificationIntent)
    }

    private fun showPauseNotificationButton() {
        customNotificationLayout.setImageViewResource(R.id.notification_button, R.drawable.ic_pause_small)
//        customNotificationLayout.setOnClickPendingIntent(R.id.notification_button, pauseNotificationIntent)
    }

    private fun startChronometer(timeFromITBeginning: Long) {
        customNotificationLayout
            .setChronometer(R.id.notification_total_time_chronometer, timeFromITBeginning + SystemClock.elapsedRealtime() - System.currentTimeMillis(), null, true)
        updateNotificationLayout()
        showPauseNotificationButton()
    }

    private fun pauseChronometer(timeFromITBeginning: Long) {
        customNotificationLayout
            .setChronometer(R.id.notification_total_time_chronometer, timeFromITBeginning, null, false)
        updateNotificationLayout()
        showStartNotificationButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}

class TimerServiceBinder(val service: TimerService) : Binder()