package pl.jurassic.roger.util.injection

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import pl.jurassic.roger.BaseRogerApp
import pl.jurassic.roger.util.injection.binding.ActivityBinderModule
import pl.jurassic.roger.util.injection.binding.FragmentBinderModule
import pl.jurassic.roger.util.injection.binding.ServiceBinderModule
import pl.jurassic.roger.util.injection.modules.DatabaseModule
import pl.jurassic.roger.util.tools.DateFormatter
import pl.jurassic.roger.util.tools.DateFormatterImpl
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class RuntimeScope

@Module
class AppModule {

    @Provides
    fun context(application: Application): Context =
            application

    @Provides
    fun eventBus(): EventBus =
            EventBus.getDefault()

    @Provides
    fun timeFormatter(): DateFormatter =
            DateFormatterImpl()

    @Provides
    fun compositeDisposable(): CompositeDisposable =
            CompositeDisposable()
}

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityBinderModule::class,
    FragmentBinderModule::class,
    ServiceBinderModule::class,
    DatabaseModule::class,
    AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: BaseRogerApp)
}
