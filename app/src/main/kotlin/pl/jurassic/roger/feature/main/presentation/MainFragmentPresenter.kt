package pl.jurassic.roger.feature.main.presentation

import org.joda.time.DateTime
import pl.jurassic.roger.data.WorkTime
import pl.jurassic.roger.data.ui.BreakProgressAngle
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

    companion object {
        private const val FULL_ANGLE_DEGREE = 360f
        private const val MINUTS_IN_HOUR = 60f
        private const val SECONDS_IN_MINUTS = 60f
        private const val MILLISECONDS_IN_SECONDS = 1000f
        private const val WORK_TIME = 8f //todo take from share-prefs
    }

    val configuration by lazy { view.timerService.configuration }

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
        view.setJobTimeProgressAngle(countProgressAngle(time))
        view.setJobTime(dateFormatter.parseTime(time))
    }

    private fun countProgressAngle(time: Long) =
        time * FULL_ANGLE_DEGREE / (WORK_TIME * MINUTS_IN_HOUR * MILLISECONDS_IN_SECONDS) //todo add SECONDS_IN_MINUTS

    override fun onBreakTimeReceive(breakType: BreakType, time: Long) {
        val breakTime = dateFormatter.parseTime(getBreakTime(breakType) + time)
        when(breakType) {
            BreakType.LUNCH -> view.setLunchTimeText(breakTime)
            BreakType.SMOKING -> view.setSmokingTimeText(breakTime)
            BreakType.OTHER -> view.setOtherTimeText(breakTime)
        }

        view.setBreakTimeProgressAngles(transformToProgressAngleList(time))
        view.setBreakTotalTime(dateFormatter.parseTime(getBreakTotalTime() + time))
    }

    private fun getBreakTotalTime(): Int {
        var totalTime = 0
        configuration.breakTimesMap.forEach { _, value -> totalTime += value
            .sumBy { (it.stopTimestamp - it.startTimestamp).toInt() } }
        return totalTime
    }


    private fun getBreakTime(breakType: BreakType): Int =
        configuration.breakTimesMap[breakType]?.sumBy { (it.stopTimestamp - it.startTimestamp).toInt() } ?: 0

    private fun transformToProgressAngleList(time: Long): List<BreakProgressAngle> {
        val list = arrayListOf<BreakProgressAngle>()
        val breakTimeMap = configuration.breakTimesMap

        breakTimeMap.forEach { type, breakTime ->
            breakTime.forEach {
                val sweepAngle = countProgressAngle(it.stopTimestamp - it.startTimestamp)
                val startAngle = countProgressAngle(it.startTimestamp - view.timerService.configuration.startTime)
                list.add(BreakProgressAngle(startAngle, sweepAngle, type.breakColorRes))
            }
        }

        list.last().sweepAngle += countProgressAngle(time)

        return list
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