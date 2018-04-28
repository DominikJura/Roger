package pl.jurassic.roger.feature.summary.ui

import android.support.v7.widget.RecyclerView
import pl.jurassic.roger.R
import pl.jurassic.roger.data.ui.SummaryWorkTime
import pl.jurassic.roger.feature.common.ui.BaseFragment
import pl.jurassic.roger.feature.summary.SummaryFragmentContract.Presenter
import pl.jurassic.roger.feature.summary.SummaryFragmentContract.View
import pl.jurassic.roger.feature.summary.ui.adapters.SummaryAdapter
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_summary.summary_work_recycler as workRecyclerView

class SummaryFragment : BaseFragment<Presenter>(), View {

    @Inject
    lateinit var summaryAdapter: SummaryAdapter

    @Inject
    lateinit var summaryLayoutManager: RecyclerView.LayoutManager

    override val layoutId: Int = R.layout.fragment_summary

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
}