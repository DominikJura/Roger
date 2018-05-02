package pl.jurassic.roger.util.timer

import pl.jurassic.roger.data.BreakTime

class TimerConfigurationImpl : TimerConfiguration {

    override var isRunning: Boolean = false

    override var initialize: Boolean = false

    override var totalJobTimeThatPass: Long = 0L

    override var startJobTime: Long = 0L

    override var breakTimesList: ArrayList<BreakTime> = arrayListOf()

}