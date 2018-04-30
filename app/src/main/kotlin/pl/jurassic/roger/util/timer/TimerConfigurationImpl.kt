package pl.jurassic.roger.util.timer

import org.joda.time.DateTime
import pl.jurassic.roger.data.BreakTime

class TimerConfigurationImpl : TimerConfiguration {

    override var isRunning: Boolean = false

    override var initialize: Boolean = false

    override var startTime: Long = DateTime.now().millis

    override var breakTimesList: ArrayList<BreakTime> = arrayListOf()

}