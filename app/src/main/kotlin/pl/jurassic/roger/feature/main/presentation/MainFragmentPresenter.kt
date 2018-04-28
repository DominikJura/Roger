package pl.jurassic.roger.feature.main.presentation

import org.joda.time.DateTime
import pl.jurassic.roger.data.WorkTime
import pl.jurassic.roger.feature.main.MainFragmentContract.Presenter
import pl.jurassic.roger.feature.main.MainFragmentContract.Router
import pl.jurassic.roger.feature.main.MainFragmentContract.View
import pl.jurassic.roger.util.repository.Repository
import pl.jurassic.roger.util.timer.BreakType
import pl.jurassic.roger.util.tools.DateFormatter

class MainFragmentPresenter(
    private val view: View,
    private val router: Router,
    private val dateFormatter: DateFormatter,
    private val repository: Repository
) : Presenter {

    override fun initialize() = Unit

    override fun clear() = Unit

    override fun onTimerButtonClicked() {
        val configuration = view.timerService.configuration
        when (configuration.isRunning) {
            true -> pauseTimer()
            false -> startTimer()
        }
    }

    private fun startTimer() = with(view) {
        timerService.startJobTimer()
        setTimerStart()
    }

    private fun pauseTimer() = with(view) {
        timerService.pauseJobTimer()
        setTimerPause()
    }

    override fun onJobTimeReceive(time: Long) {
//        view.setTimeProgress()
        view.setJobTime(dateFormatter.parseTime(time))
    }

    override fun onBreakTimeReceive(time: Long) {
        view.setBreakTime(dateFormatter.parseTime(time))
    }

    override fun onSmokingItemClicked(isSelected: Boolean) = with(view) {
        when (isSelected) {
            true -> {
                pauseBreakTimer()
                deactivateSmokingButton()
            }
            false -> {
                startBreakTimer(BreakType.SMOKING)

                deactivateOtherButton()
                deactivateLunchButton()
                activeSmokingButton()
            }
        }
    }

    private fun pauseBreakTimer() = with(view) {
        activeBreakType?.let { timerService.pauseBreakTimer(it) }
        activeBreakType = null
    }

    private fun startBreakTimer(breakType: BreakType) = with(view) {
        activeBreakType?.let { timerService.pauseBreakTimer(it) }
        activeBreakType = breakType
        timerService.startBreakTimer(breakType)
    }

    override fun onLunchItemClicked(isSelected: Boolean) = with(view) {
        when (isSelected) {
            true -> {
                pauseBreakTimer()
                deactivateLunchButton()
            }
            false -> {
                startBreakTimer(BreakType.LUNCH)

                deactivateOtherButton()
                deactivateSmokingButton()
                activeLunchButton()
            }
        }
    }

    override fun onOtherItemClicked(isSelected: Boolean) = with(view) {
        when (isSelected) {
            true -> {
                pauseBreakTimer()
                deactivateOtherButton()
            }
            false -> {
                startBreakTimer(BreakType.OTHER)

                deactivateSmokingButton()
                deactivateLunchButton()
                activeOtherButton()
            }
        }
    }

    override fun onSaveClicked() = with(view.timerService.configuration) {
        val workTime = WorkTime(jobTimeThatAlreadyPass, breakTypeTotalTime, DateTime.now())
        repository.saveWorkTime(workTime)

        router.navigateToSummaryScreen()
    }
}