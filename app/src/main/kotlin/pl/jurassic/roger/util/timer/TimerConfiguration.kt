package pl.jurassic.roger.util.timer

import android.support.annotation.ColorRes
import pl.jurassic.roger.R
import pl.jurassic.roger.data.BreakTime

interface TimerConfiguration {

    var isRunning: Boolean
    var initialize: Boolean
    var startTime: Long

    var breakTimesList: ArrayList<BreakTime>
}

enum class BreakType(@ColorRes val breakColorRes: Int) {
    LUNCH(R.color.break_lunch_color),
    SMOKING(R.color.break_smoking_color),
    OTHER(R.color.break_other_color)
}