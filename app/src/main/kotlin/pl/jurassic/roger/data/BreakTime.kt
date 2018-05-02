package pl.jurassic.roger.data

import pl.jurassic.roger.util.timer.BreakType

data class BreakTime(
    val breakType: BreakType,
    var startTimestamp: Long,
    var jobTimeThatPass: Long,
    var stopTimestamp: Long = 0L
)