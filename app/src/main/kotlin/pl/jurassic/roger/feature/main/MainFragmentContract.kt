package pl.jurassic.roger.feature.main

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
        fun setTimerPause()
        fun setTimerStart()
        fun setBreakTime(breakTime: String)
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
        fun onBreakTimeReceive(time: Long)
    }
}