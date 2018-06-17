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
import pl.jurassic.roger.data.BreakType
import pl.jurassic.roger.util.tools.DateFormatter

class RepositoryImpl(
    private val workTimeDao: WorkTimeDao,
    private val dateFormatter: DateFormatter
) : Repository {

    override fun getWorkTimeChartData(): Observable<List<BreakBarData>> =
            workTimeDao.getAllWorkTime()
                    .toObservable()
                    .map { addMissingDays(it) }
                    .flatMapIterable { it }
                    .map { transformWorkTimeToBarEntry(it) }
                    .flatMapIterable { it }
                    .toList()
                    .toObservable()


    private fun addMissingDays(workList: List<WorkTimeData>): ArrayList<WorkTimeData> {
        val totalWorkList = arrayListOf<WorkTimeData>()

        val firstElementDate = workList.first().jobTimeData.dateTimeKey

        for (i in (1 until firstElementDate.dayOfWeek).reversed()) {
            totalWorkList.add(getEmptyWorkTimeData(firstElementDate.minusDays(i)))
        }

        for (i in 0 until workList.size - 1) {
            totalWorkList.add(workList[i])
            val workTime = workList[i + 1].jobTimeData.dateTimeKey.dayOfYear - workList[i].jobTimeData.dateTimeKey.dayOfYear
            for (j in 0 until workTime) {
                totalWorkList.add(getEmptyWorkTimeData(workList[i].jobTimeData.dateTimeKey.plusDays(j)))
            }
        }

        totalWorkList.add(workList.last())
        return totalWorkList
    }

    private fun getEmptyWorkTimeData(data: DateTime): WorkTimeData {
        val emptyWorkTime = WorkTimeData()
        emptyWorkTime.jobTimeData = JobTimeData(data, DateTime.now(), DateTime.now(), 0L)
        emptyWorkTime.breakTimeList = arrayListOf(
                BreakTimeData(data, BreakType.LUNCH, DateTime.now(), DateTime.now()),
                BreakTimeData(data, BreakType.OTHER, DateTime.now(), DateTime.now()),
                BreakTimeData(data, BreakType.SMOKING, DateTime.now(), DateTime.now())
        )
        return emptyWorkTime
    }

    private fun transformWorkTimeToBarEntry(workTimeData: WorkTimeData): List<BreakBarData> {
        return workTimeData.breakTimeList
                .groupBy { it -> it.breakType }
                .map {
                    BreakBarData(it.key,
                            workTimeData.jobTimeData.dateTimeKey,
                            it.value.sumByLong { getTimeDifference(it) }
                    )
                }
    }

    private fun getTimeDifference(breakTimeData: BreakTimeData) =
            breakTimeData.stopDateTime.millis - breakTimeData.startDateTime.millis

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

        val summaryBreakTimeList = breakTimeList
                .groupBy { it -> it.breakType }
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

        val breakTimeList = breakTime.map {
            BreakTimeData(dateTime, it.breakType, DateTime(it.startTimestamp), DateTime(it.stopTimestamp))
        }

        workTimeDao.insertWorkTime(jobTimeData, breakTimeList)
    }
}