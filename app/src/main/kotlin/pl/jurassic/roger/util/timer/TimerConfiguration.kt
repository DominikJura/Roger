package pl.jurassic.roger.util.timer

import android.support.annotation.ColorRes
import pl.jurassic.roger.R
import pl.jurassic.roger.data.BreakTime
import java.util.Queue

interface TimerConfiguration {

    var isRunning: Boolean

    var startTime: Long
    var jobTimeThatAlreadyPass: Long

    var breakTypeTotalTime: MutableMap<BreakType, Long>
    var breakTypeStartTime: MutableMap<BreakType, Long>
    var breakTimesMap: MutableMap<BreakType, Queue<BreakTime>>
}

enum class BreakType(@ColorRes val breakColorRes: Int) {
    LUNCH(R.color.break_lunch_color),
    SMOKING(R.color.break_smoking_color),
    OTHER(R.color.break_other_color)
}