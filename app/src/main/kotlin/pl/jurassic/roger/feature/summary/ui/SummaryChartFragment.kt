package pl.jurassic.roger.feature.summary.ui

import pl.jurassic.roger.R
import pl.jurassic.roger.feature.common.ui.BaseFragment
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract.View

class SummaryChartFragment : BaseFragment<Presenter>(), View {

    override val layoutId: Int = R.layout.fragment_summary_chart
}