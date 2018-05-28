package pl.jurassic.roger.util.timer

import android.app.PendingIntent
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
import pl.jurassic.roger.OnClickedListener
import pl.jurassic.roger.R
import pl.jurassic.roger.data.BreakTime
import pl.jurassic.roger.util.config.StringConstants.INTENT_ACTION_PAUSE_TIMER
import pl.jurassic.roger.util.config.StringConstants.INTENT_ACTION_RESUME_TIMER
import pl.jurassic.roger.util.tools.JobTimer
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

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
    @field:Named("TimerServicePauseTimerIntent")
    lateinit var pauseNotificationIntent: PendingIntent

    @Inject
    @field:Named("TimerServiceResumeTimerIntent")
    lateinit var resumeNotificationIntent: PendingIntent

    @Inject
    lateinit var customNotificationLayout: RemoteViews

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var jobTimeDisposable: Disposable
    private lateinit var breakTimeDisposable: Disposable

    var timeUpdateCallback: TimeUpdateCallback? = null
    var breakUpdateCallback: ((breakType: BreakType, time: Long) -> Unit)? = null
    var onNotificationPauseClicked: OnClickedListener? = null
    var onNotificationResumeClicked: OnClickedListener? = null

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onBind(intent: Intent?): IBinder =
        TimerServiceBinder(this)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                INTENT_ACTION_PAUSE_TIMER -> {
                    pauseNotificationTimer()
                    onNotificationPauseClicked?.invoke()
                }
                INTENT_ACTION_RESUME_TIMER -> {
                    startNotificationsTimer()
                    onNotificationResumeClicked?.invoke()
                }
                else -> Timber.e("Unknown intent action: ${it.action}")
            }
        }

        return START_NOT_STICKY
    }

    private fun startNotificationsTimer() { //TODO rename
        if (!configuration.isRunning) {

            startChronometer(DateTime.now().millis - configuration.totalJobTimeThatPass)

            configuration.isRunning = true

            configuration.startJobTime = DateTime.now()

            jobTimeDisposable = jobTimer.timerObservable(configuration.startJobTime.millis, configuration.totalJobTimeThatPass)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { configuration.timeThatPass = it }
                    .subscribe({ timeUpdateCallback?.invoke(it) }, { Timber.e(it) })

            compositeDisposable.add(jobTimeDisposable)
        }
    }

    private fun pauseNotificationTimer() {
        compositeDisposable.remove(jobTimeDisposable)

        configuration.totalJobTimeThatPass += DateTime.now().millis - configuration.startJobTime.millis
        pauseChronometer(DateTime.now().millis - configuration.totalJobTimeThatPass)

        configuration.isRunning = false
    }

    fun startBreakTimer(breakType: BreakType) {
        val breakStartTimestamp = DateTime.now().millis

        configuration.breakTimesList.add(BreakTime(
            breakType,
            breakStartTimestamp,
            configuration.timeThatPass,
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
        if (!configuration.isRunning) {
            startNotificationsTimer()
            startForeground(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    fun pauseJobTimer() {
        pauseNotificationTimer()
        stopForeground(true)
    }

    private fun updateNotificationLayout() {
        notificationBuilder.setCustomContentView(customNotificationLayout)
        notificationBuilder.setCustomBigContentView(customNotificationLayout)
        notificationBuilder.setCustomHeadsUpContentView(customNotificationLayout)
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun showStartNotificationButton() {
        customNotificationLayout.setImageViewResource(R.id.notification_button, R.drawable.ic_play_small)
        customNotificationLayout.setOnClickPendingIntent(R.id.notification_button, resumeNotificationIntent)
    }

    private fun showPauseNotificationButton() {
        customNotificationLayout.setImageViewResource(R.id.notification_button, R.drawable.ic_pause_small)
        customNotificationLayout.setOnClickPendingIntent(R.id.notification_button, pauseNotificationIntent)
    }

    private fun startChronometer(timeFromITBeginning: Long) {
        customNotificationLayout
            .setChronometer(R.id.notification_total_time_chronometer, timeFromITBeginning + SystemClock.elapsedRealtime() - System.currentTimeMillis(), null, true)
        showPauseNotificationButton()
        updateNotificationLayout()
    }

    private fun pauseChronometer(timeFromITBeginning: Long) {
        customNotificationLayout
            .setChronometer(R.id.notification_total_time_chronometer, timeFromITBeginning + SystemClock.elapsedRealtime() - System.currentTimeMillis(), null, false)
        showStartNotificationButton()
        updateNotificationLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}

class TimerServiceBinder(val service: TimerService) : Binder()