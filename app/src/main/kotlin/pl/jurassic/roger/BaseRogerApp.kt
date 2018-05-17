package pl.jurassic.roger

import android.app.Activity
import android.app.Application
import android.app.Service
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import io.fabric.sdk.android.Fabric
import net.danlew.android.joda.JodaTimeAndroid
import pl.jurassic.roger.util.injection.DaggerAppComponent
import javax.inject.Inject

abstract class BaseRogerApp : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()
        init()
    }

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    open fun init() {
        initFabric()
        initTimber()
        initJodaTime()
        initAppComponent()
    }

    private fun initJodaTime() {
        JodaTimeAndroid.init(this)
    }

    private fun initFabric() {
        if (BuildConfig.FLAVOR != "develop") {
            Fabric.with(this, Crashlytics(), Answers())
        }
    }

    private fun initAppComponent() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    abstract fun initTimber()
}
