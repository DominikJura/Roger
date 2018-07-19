package pl.jurassic.roger.util.injection.modules.main

import android.content.Context
import android.content.Intent
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.greenrobot.eventbus.EventBus
import pl.jurassic.roger.feature.main.MainFragmentContract
import pl.jurassic.roger.feature.main.navigation.MainFragmentRouter
import pl.jurassic.roger.feature.main.presentation.MainFragmentPresenter
import pl.jurassic.roger.feature.main.ui.MainFragment
import pl.jurassic.roger.util.injection.RuntimeScope
import pl.jurassic.roger.util.repository.Repository
import pl.jurassic.roger.util.timer.TimerService
import pl.jurassic.roger.util.tools.DateFormatter

@Module
class MainFragmentModule {

    @Provides
    fun view(fragment: MainFragment): MainFragmentContract.View = fragment

    @Provides
    fun router(eventBus: EventBus): MainFragmentContract.Router =
            MainFragmentRouter(eventBus)

    @Provides
    fun timerServiceIntent(context: Context): Intent =
            Intent(context, TimerService::class.java)

    @Provides
    fun subjectLong(): Subject<Long> = PublishSubject.create()

    @RuntimeScope
    @Provides
    fun presenter(
        view: MainFragmentContract.View,
        router: MainFragmentContract.Router,
        dateFormatter: DateFormatter,
        repository: Repository,
        subjectJob: Subject<Long>,
        subjectBreak: Subject<Long>,
        compositeDisposable: CompositeDisposable
    ): MainFragmentContract.Presenter =
            MainFragmentPresenter(
                view,
                router,
                dateFormatter,
                repository,
                subjectJob,
                subjectBreak,
                compositeDisposable
            )
}