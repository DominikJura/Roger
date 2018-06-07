package pl.jurassic.roger.data

data class BreakTime(
    val breakType: BreakType,
    var startTimestamp: Long,
    var jobTimeThatPass: Long,
    var stopTimestamp: Long = 0L
)