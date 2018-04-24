package pl.jurassic.roger.util.injection.binding

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.jurassic.roger.feature.main.ui.MainActivity
import pl.jurassic.roger.feature.summary.ui.SummaryActivity
import pl.jurassic.roger.util.injection.RuntimeScope
import pl.jurassic.roger.util.injection.modules.main.MainActivityModule
import pl.jurassic.roger.util.injection.modules.summary.SummaryActivityModule

@Module
abstract class ActivityBinderModule {

    @Binds
    abstract fun activity(activity: AppCompatActivity): AppCompatActivity

    @RuntimeScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @RuntimeScope
    @ContributesAndroidInjector(modules = [SummaryActivityModule::class])
    abstract fun bindSummaryActivity(): SummaryActivity
}
