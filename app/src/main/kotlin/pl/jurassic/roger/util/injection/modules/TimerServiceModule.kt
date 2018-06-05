package pl.jurassic.roger.util.injection.modules

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.Disposable
import pl.jurassic.roger.R
import pl.jurassic.roger.feature.main.ui.MainActivity
import pl.jurassic.roger.util.config.StringConstants.INTENT_ACTION_PAUSE_TIMER
import pl.jurassic.roger.util.config.StringConstants.INTENT_ACTION_RESUME_TIMER
import pl.jurassic.roger.data.BreakType
import pl.jurassic.roger.util.timer.TimerConfiguration
import pl.jurassic.roger.util.timer.TimerConfigurationImpl
import pl.jurassic.roger.util.timer.TimerService
import pl.jurassic.roger.util.tools.JobTimer
import pl.jurassic.roger.util.tools.JobTimerImpl
import javax.inject.Named

@Module
class TimerServiceModule {

    @Provides
    fun timerConfiguration(): TimerConfiguration = TimerConfigurationImpl()

    @Provides
    fun timeMap(): MutableMap<BreakType, Long> = mutableMapOf()

    @Provides
    fun disposableMap(): MutableMap<BreakType, Disposable> = mutableMapOf()

    @Provides
    fun jobTimer(): JobTimer = JobTimerImpl()

    @Provides
    fun customLayout(context: Context): RemoteViews =
        RemoteViews(context.packageName, R.layout.view_timer_service_notification)

    @Provides
    @Named("TimerServiceMainIntent")
    fun mainIntent(context: Context) = Intent(context, MainActivity::class.java)

    @Provides
    @Named("TimerServiceIntent")
    fun timerServiceIntent(context: Context) = Intent(context, TimerService::class.java)

    @Provides
    @Named("TimerServicePauseTimerIntent")
    fun pauseTimerIntent(@Named("TimerServiceIntent") serviceIntent: Intent, context: Context): PendingIntent =
        getServiceIntent(context, serviceIntent, INTENT_ACTION_PAUSE_TIMER)

    private fun getServiceIntent(context: Context, mainIntent: Intent, action: String): PendingIntent {
        mainIntent.action = action
        return PendingIntent.getService(context, 0, mainIntent, 0)
    }

    @Provides
    @Named("TimerServiceResumeTimerIntent")
    fun resumeTimerIntent(@Named("TimerServiceIntent") serviceIntent: Intent, context: Context): PendingIntent =
        getServiceIntent(context, serviceIntent, INTENT_ACTION_RESUME_TIMER)

    @Provides
    @Named("TimerServiceNotificationIntent")
    fun notificationIntent(context: Context, @Named("TimerServiceMainIntent") mainIntent: Intent): PendingIntent =
        PendingIntent.getActivity(context, 0, mainIntent, 0)

    @Provides
    fun notification(
        @Named("TimerServiceNotificationIntent") notificationIntent: PendingIntent,
        customLayout: RemoteViews,
        context: Context
    ): NotificationCompat.Builder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initNotificationChannel(context, "RogerID", "RogerChannel")
        }
        return NotificationCompat.Builder(context, "TimerServiceNotificationChannel")
            .setSmallIcon(R.drawable.ic_notification_small)
            .setOngoing(true)
            .setUsesChronometer(true)
            .setContentIntent(notificationIntent)
            .setCustomBigContentView(customLayout)
            .setChannelId("RogerID")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initNotificationChannel(context: Context, channelId: String, channelName: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}