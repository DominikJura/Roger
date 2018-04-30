package pl.jurassic.roger.util.timer

import org.joda.time.DateTime
import pl.jurassic.roger.data.BreakTime
import java.util.LinkedList
import java.util.Queue

class TimerConfigurationImpl : TimerConfiguration {

    override var breakTimesMap: MutableMap<BreakType, Queue<BreakTime>> = initMap()

    private fun initMap(): MutableMap<BreakType, Queue<BreakTime>> =
        mutableMapOf(
            BreakType.SMOKING to LinkedList(),
            BreakType.LUNCH to LinkedList(),
            BreakType.OTHER to LinkedList<BreakTime>()
        )

    override var breakTypeStartTime: MutableMap<BreakType, Long> = mutableMapOf()

    override var breakTypeTotalTime: MutableMap<BreakType, Long> = mutableMapOf()

    override var startTime: Long = DateTime.now().millis

    override var jobTimeThatAlreadyPass: Long = 0L

    override var isRunning: Boolean = false
}