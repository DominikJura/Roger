package pl.jurassic.roger.util.injection.binding

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.jurassic.roger.util.injection.RuntimeScope
import pl.jurassic.roger.util.injection.modules.TimerServiceModule
import pl.jurassic.roger.util.timer.TimerService

@Module
abstract class ServiceBinderModule {

    @RuntimeScope
    @ContributesAndroidInjector(modules = [TimerServiceModule::class])
    abstract fun bindTimerService(): TimerService
}