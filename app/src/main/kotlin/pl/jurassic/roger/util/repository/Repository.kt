package pl.jurassic.roger.util.repository

import io.reactivex.Observable
import pl.jurassic.roger.data.WorkTime
import pl.jurassic.roger.data.ui.SummaryWorkTime

interface Repository {

    fun getWorkTimeList(): Observable<List<SummaryWorkTime>>
    fun saveWorkTime(workTime: WorkTime)
}