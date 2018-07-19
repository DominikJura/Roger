package pl.jurassic.roger.feature.summary.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import pl.jurassic.roger.R
import pl.jurassic.roger.feature.common.ui.BaseActivity
import pl.jurassic.roger.feature.summary.SummaryActivityContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryActivityContract.View
import pl.jurassic.roger.feature.summary.ui.adapters.SummaryFragments
import pl.jurassic.roger.feature.summary.ui.adapters.SummaryPageAdapter
import pl.jurassic.roger.getColor
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_summary.summary_tab as viewPagerTabLayout
import kotlinx.android.synthetic.main.activity_summary.summary_toolbar as toolbar
import kotlinx.android.synthetic.main.activity_summary.summary_view_pager as viewPager

class SummaryActivity : BaseActivity<Presenter>(), View {

    @Inject
    lateinit var summaryPageAdapter: SummaryPageAdapter

    override val layoutId: Int = R.layout.activity_summary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initViewPager()
    }

    private fun initToolbar() = with(toolbar) {
        setSupportActionBar(this)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val drawable = getDrawable(R.drawable.ic_arrow_back)
        drawable.setColorFilter(getColor(R.color.lightish_blue), PorterDuff.Mode.SRC_ATOP)
        navigationIcon = drawable
        setNavigationOnClickListener { presenter.onBackPressed() }
    }

    private fun initViewPager() {
        viewPager.adapter = summaryPageAdapter
        viewPagerTabLayout.setCustomTabView({ container, position, _ ->
            val inflater = LayoutInflater.from(container.context)
            val icon = inflater.inflate(R.layout.view_summary_pager_item, container, false) as ImageView
            icon.setImageDrawable(getDrawable(SummaryFragments.values()[position].drawableRes))

            return@setCustomTabView icon
        })
        viewPagerTabLayout.setViewPager(viewPager)
    }

    override fun navigateBack() {
        onBackPressed()
    }
}