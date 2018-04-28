package pl.jurassic.roger.util.tools

import io.reactivex.Observable

interface JobTimer {

    fun timerObservable(startTimestamp: Long, totalTimePass: Long = 0L): Observable<Long>
}