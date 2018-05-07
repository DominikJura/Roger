package pl.jurassic.roger.util.repository

import io.reactivex.Observable
import pl.jurassic.roger.data.WorkTime
import pl.jurassic.roger.data.ui.BreakBarData
import pl.jurassic.roger.data.ui.SummaryWorkTime

interface Repository {

    fun getWorkTimeList(): Observable<List<SummaryWorkTime>>
    fun getWorkTimeChartData(): Observable<List<BreakBarData>>
    fun saveWorkTime(workTime: WorkTime)
}