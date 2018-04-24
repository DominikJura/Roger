package pl.jurassic.roger.util.timer

import org.joda.time.DateTime

class TimerConfigurationImpl : TimerConfiguration {

    override var breakTypeStartTime: MutableMap<BreakType, Long> = mutableMapOf()

    override var breakTypeTotalTime: MutableMap<BreakType, Long> = mutableMapOf()

    override var startTime: Long = DateTime.now().millis

    override var jobTimeThatAlreadyPass: Long = 0L

    override var isRunning: Boolean = false
}