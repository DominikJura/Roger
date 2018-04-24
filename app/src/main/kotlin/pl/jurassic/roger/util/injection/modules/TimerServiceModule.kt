package pl.jurassic.roger.util.injection.modules

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.Disposable
import pl.jurassic.roger.util.injection.RuntimeScope
import pl.jurassic.roger.util.timer.*
import pl.jurassic.roger.util.tools.JobTimer
import pl.jurassic.roger.util.tools.JobTimerImpl

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
}