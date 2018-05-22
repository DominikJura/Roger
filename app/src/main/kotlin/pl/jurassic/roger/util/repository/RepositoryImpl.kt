package pl.jurassic.roger.util.repository

import io.reactivex.Observable
import org.joda.time.DateTime
import pl.jurassic.roger.data.WorkTime
import pl.jurassic.roger.data.database.BreakTimeData
import pl.jurassic.roger.data.database.JobTimeData
import pl.jurassic.roger.data.database.WorkTimeData
import pl.jurassic.roger.data.ui.BreakBarData
import pl.jurassic.roger.data.ui.SummaryBreakTime
import pl.jurassic.roger.data.ui.SummaryWorkTime
import pl.jurassic.roger.sumByLong
import pl.jurassic.roger.util.database.WorkTimeDao
import pl.jurassic.roger.util.timer.BreakType
import pl.jurassic.roger.util.tools.DateFormatter

class RepositoryImpl(
    private val workTimeDao: WorkTimeDao,
    private val dateFormatter: DateFormatter
) : Repository {

    override fun getWorkTimeChartData(): Observable<List<BreakBarData>> =
        workTimeDao.getAllWorkTime()
            .toObservable()
            .flatMapIterable { it }
            .map { transformWorkTimeToBarEntry(it) }
            .flatMapIterable { it }
            .toList()
            .toObservable()

    private fun transformWorkTimeToBarEntry(workTimeData: WorkTimeData): List<BreakBarData> =
        workTimeData.breakTimeList
            .groupBy { it -> it.breakType }
            .map { BreakBarData(it.key, workTimeData.jobTimeData.dateTimeKey, it.value.sumByLong { getTimeDifference(it) }) }

    private fun getTimeDifference(breakTimeData: BreakTimeData) =
        breakTimeData.stopTimestamp - breakTimeData.startTimestamp

    override fun getWorkTimeList(): Observable<List<SummaryWorkTime>> =
        workTimeDao
            .getAllWorkTime()
            .toObservable()
            .flatMapIterable { it }
            .map { transformWorkTimeData(it) }
            .toList()
            .toObservable()

    private fun transformWorkTimeData(workTime: WorkTimeData): SummaryWorkTime = with(workTime) {
        val workDateTime = dateFormatter.parseDate(jobTimeData.dateTimeKey)
        val jobTime = dateFormatter.parseTime(jobTimeData.jobTime)
        val breakTotalTime = dateFormatter.parseTime(breakTimeList.sumByLong { getTimeDifference(it) })

        val summaryBreakTimeList = breakTimeList.groupBy { it -> it.breakType }
            .map { transformBreakTimeData(it.key, it.value.sumByLong { getTimeDifference(it) }) }
            .sortedBy { it.breakType.ordinal }

        return SummaryWorkTime(workDateTime, jobTime, breakTotalTime, summaryBreakTimeList)
    }

    private fun transformBreakTimeData(breakType: BreakType, breakTotalTime: Long): SummaryBreakTime {
        val breakTime = dateFormatter.parseTime(breakTotalTime)
        return SummaryBreakTime(breakType, breakTime)
    }

    override fun saveWorkTime(workTime: WorkTime) = with(workTime) {
        val dateTime = DateTime.now()
        val jobTimeData = JobTimeData(dateTime, startJobTime, endDateTime, jobTime)

        breakTime.forEach {
            workTimeDao.insertBreakTime(BreakTimeData(dateTime, it.breakType, it.startTimestamp, it.stopTimestamp))
        }

        workTimeDao.insertJobTime(jobTimeData)
    }
}