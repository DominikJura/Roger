package pl.jurassic.roger.util.timer

import org.joda.time.DateTime
import pl.jurassic.roger.data.BreakTime
import pl.jurassic.roger.data.BreakType

interface TimerConfiguration {

    var isRunning: Boolean
    var activeBreakType: BreakType?

    var startJobTime: DateTime
    var totalJobTimeThatPass: Long

    var timeThatPass: Long //todo refactor

    var breakTimesList: ArrayList<BreakTime>
}

