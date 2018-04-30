package pl.jurassic.roger.util.repository

import io.reactivex.Observable
import org.joda.time.DateTime
import pl.jurassic.roger.data.WorkTime
import pl.jurassic.roger.data.database.BreakTimeData
import pl.jurassic.roger.data.database.JobTimeData
import pl.jurassic.roger.data.database.WorkTimeData
import pl.jurassic.roger.data.ui.SummaryBreakTime
import pl.jurassic.roger.data.ui.SummaryWorkTime
import pl.jurassic.roger.sumByLong
import pl.jurassic.roger.util.database.WorkTimeDao
import pl.jurassic.roger.util.tools.DateFormatter

class RepositoryImpl(
    private val workTimeDao: WorkTimeDao,
    private val dateFormatter: DateFormatter
) : Repository {

    override fun getWorkTimeList(): Observable<List<SummaryWorkTime>> =
            workTimeDao
                    .getAllWorkTime()
                    .toObservable()
                    .flatMapIterable { it }
                    .map { transformWorkTimeData(it) }
                    .toList()
                    .toObservable()

    private fun transformWorkTimeData(workTime: WorkTimeData): SummaryWorkTime = with(workTime) {
        val workDateTime = dateFormatter.parseDate(jobTimeData.dateTime)
        val jobTime = dateFormatter.parseTime(jobTimeData.jobTime)
        val breakTotalTime = dateFormatter.parseTime(breakTimeList
            .sumByLong { (it.stopTimestamp - it.startTimestamp) })

        val summaryBreakTimeList = breakTimeList.map { transformBreakTimeData(it) }

        return SummaryWorkTime(workDateTime, jobTime, breakTotalTime, summaryBreakTimeList)
    }

    private fun transformBreakTimeData(breakTimeData: BreakTimeData): SummaryBreakTime {
        val breakTime = dateFormatter.parseTime(breakTimeData.stopTimestamp - breakTimeData.startTimestamp)
        return SummaryBreakTime(breakTimeData.breakType, breakTime)
    }

    override fun saveWorkTime(workTime: WorkTime) = with(workTime) {
        val dateTime = DateTime.now()
        val jobTimeData = JobTimeData(dateTime, jobTime)

        breakTime.forEach {
            workTimeDao.insertBreakTime(BreakTimeData(it.breakType, dateTime, it.startTimestamp, it.stopTimestamp))
        }

        workTimeDao.insertJobTime(jobTimeData)
    }
}