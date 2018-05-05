package pl.jurassic.roger.feature.summary.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import pl.jurassic.roger.R
import pl.jurassic.roger.feature.summary.ui.SummaryChartFragment
import pl.jurassic.roger.feature.summary.ui.SummaryListFragment

class SummaryPageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = when (position) {
        SummaryFragments.SUMMARY_LIST.ordinal -> SummaryListFragment()
        SummaryFragments.SUMMARY_CHART.ordinal -> SummaryChartFragment()
        else -> throw RuntimeException("Illegal summary page position: $position")
    }

    override fun getCount(): Int = SummaryFragments.values().size
}

enum class SummaryFragments(val drawableRes: Int) {
    SUMMARY_LIST(R.drawable.ic_arrow_back),
    SUMMARY_CHART(R.drawable.ic_arrow_back)
}