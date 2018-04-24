package pl.jurassic.roger.util.timer

interface TimerConfiguration {

    var isRunning: Boolean

    var startTime: Long
    var jobTimeThatAlreadyPass: Long

    var breakTypeTotalTime: MutableMap<BreakType, Long>
    var breakTypeStartTime: MutableMap<BreakType, Long>
}

enum class BreakType {
    LUNCH, SMOKING, OTHER
}