package pl.jurassic.roger.util.injection.binding

import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.jurassic.roger.feature.main.ui.MainFragment
import pl.jurassic.roger.feature.summary.ui.SummaryFragment
import pl.jurassic.roger.util.injection.RuntimeScope
import pl.jurassic.roger.util.injection.modules.main.MainFragmentModule
import pl.jurassic.roger.util.injection.modules.summary.SummaryFragmentModule

@Module
abstract class FragmentBinderModule {

    @Binds
    abstract fun fragment(fragment: Fragment): Fragment

    @RuntimeScope
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun bindMainFragment(): MainFragment

    @RuntimeScope
    @ContributesAndroidInjector(modules = [SummaryFragmentModule::class])
    abstract fun bindSummaryFragment(): SummaryFragment
}
