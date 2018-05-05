package pl.jurassic.roger.feature.main

import pl.jurassic.roger.data.ui.ProgressAngles
import pl.jurassic.roger.feature.common.BaseContract
import pl.jurassic.roger.util.timer.BreakType
import pl.jurassic.roger.util.timer.TimerService

interface MainFragmentContract {

    interface View {
        var timerService: TimerService
        var timerServiceBound: Boolean
        var activeBreakType: BreakType?

        fun activeSmokingButton()
        fun activeLunchButton()
        fun activeOtherButton()
        fun deactivateSmokingButton()
        fun deactivateLunchButton()
        fun deactivateOtherButton()
        fun setJobTime(jobTime: String)
        fun deactivateJobButton()
        fun activeJobButton()
        fun setBreakTotalTime(breakTime: String)
        fun setLunchTimeText(breakTime: String)
        fun setSmokingTimeText(breakTime: String)
        fun setOtherTimeText(breakTime: String)
        fun hideSaveButton()
        fun showSaveButton()
        fun setProgressAngles(progressAngles: ProgressAngles)
    }

    interface Router {
        fun navigateToSummaryScreen()
    }

    interface Presenter : BaseContract.Presenter {
        fun onSmokingItemClicked(isSelected: Boolean)
        fun onLunchItemClicked(isSelected: Boolean)
        fun onOtherItemClicked(isSelected: Boolean)
        fun onSaveClicked()
        fun onTimerButtonClicked()
        fun onJobTimeReceive(time: Long)
        fun onBreakTimeReceive(breakType: BreakType, time: Long)
        fun onServiceConnect()
    }
}