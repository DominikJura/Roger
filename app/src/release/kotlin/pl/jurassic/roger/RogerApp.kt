package pl.jurassic.roger

import pl.jurassic.roger.util.ReleaseTree
import timber.log.Timber

class RogerApp : BaseRogerApp() {

    override fun initTimber() {
        Timber.plant(ReleaseTree())
    }
}
