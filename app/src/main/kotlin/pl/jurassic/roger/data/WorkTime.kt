package pl.jurassic.roger.data

import org.joda.time.DateTime
import pl.jurassic.roger.util.timer.BreakType

data class WorkTime(
    val jobTime: Long,
    val breakTime: Map<BreakType, Long>,
    val dateTime: DateTime
)