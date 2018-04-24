package pl.jurassic.roger.feature.summary.ui

import pl.jurassic.roger.R
import pl.jurassic.roger.feature.common.ui.BaseActivity
import pl.jurassic.roger.feature.summary.SummaryActivityContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryActivityContract.View

class SummaryActivity : BaseActivity<Presenter>(), View {

    override val layoutId: Int = R.layout.activity_summary

    override fun showSummaryFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.summary_fragment_root, SummaryFragment())
                .commit()
    }
}