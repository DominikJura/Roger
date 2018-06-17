package pl.jurassic.roger.feature.summary.ui

import android.support.v7.widget.RecyclerView
import android.view.View.VISIBLE
import pl.jurassic.roger.R
import pl.jurassic.roger.data.ui.SummaryWorkTime
import pl.jurassic.roger.feature.common.ui.BaseFragment
import pl.jurassic.roger.feature.summary.SummaryListFragmentContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryListFragmentContract.View
import pl.jurassic.roger.feature.summary.ui.adapters.SummaryListAdapter
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_summary_list.summary_list_work_recycler as workRecyclerView
import kotlinx.android.synthetic.main.fragment_summary_list.summary_list_no_data as noDataTextView

class SummaryListFragment : BaseFragment<Presenter>(), View {

    @Inject
    lateinit var summaryAdapter: SummaryListAdapter

    @Inject
    lateinit var summaryLayoutManager: RecyclerView.LayoutManager

    override val layoutId: Int = R.layout.fragment_summary_list

    override fun initialize() {
        initRecyclerView()
    }

    private fun initRecyclerView() = with(workRecyclerView) {
        layoutManager = summaryLayoutManager
        adapter = summaryAdapter
    }

    override fun setWorkTimeList(summaryWorkList: List<SummaryWorkTime>) {
        summaryAdapter.submitList(summaryWorkList)
    }

    override fun showNoDataText() {
        noDataTextView.visibility = VISIBLE
    }
}