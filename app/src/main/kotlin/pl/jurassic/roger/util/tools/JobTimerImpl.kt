package pl.jurassic.roger.util.tools

import io.reactivex.Observable
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit

class JobTimerImpl : JobTimer {

    override fun timerObservable(startTimestamp: Long, totalTimePass: Long): Observable<Long> =
            Observable.interval(1000L, TimeUnit.MILLISECONDS)
                    .flatMap { Observable.just(countTimePass(startTimestamp, totalTimePass)) }

    private fun countTimePass(startTimestamp: Long, totalTimeAlreadyPass: Long) =
            DateTime.now().millis - startTimestamp + totalTimeAlreadyPass
}