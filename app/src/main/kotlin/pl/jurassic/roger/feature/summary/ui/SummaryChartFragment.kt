package pl.jurassic.roger.feature.summary.ui

import android.support.v7.widget.RecyclerView
import pl.jurassic.roger.R
import pl.jurassic.roger.data.ui.summary.SummaryChartData
import pl.jurassic.roger.feature.common.ui.BaseFragment
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryChartFragmentContract.View
import pl.jurassic.roger.feature.summary.ui.adapters.SummaryChartAdapter
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_summary_chart.summary_chart_no_data as noDataTextView
import kotlinx.android.synthetic.main.fragment_summary_chart.summary_chart_recycler as chartRecyclerView


class SummaryChartFragment : BaseFragment<Presenter>(), View {

    @Inject
    lateinit var summaryAdapter: SummaryChartAdapter

    @Inject
    lateinit var summaryLayoutManager: RecyclerView.LayoutManager

    override val layoutId: Int = R.layout.fragment_summary_chart

    override fun initialize() {
        initRecyclerView()
    }

    private fun initRecyclerView() = with(chartRecyclerView) {
        layoutManager = summaryLayoutManager
        adapter = summaryAdapter
    }

    override fun setChartListData(chartListData: List<SummaryChartData>) {
        summaryAdapter.submitList(chartListData)
    }

    override fun showNoDataText() {
        noDataTextView.visibility = android.view.View.VISIBLE
    }
}