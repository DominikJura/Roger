package pl.jurassic.roger.util.timer

import android.support.annotation.ColorRes
import pl.jurassic.roger.R
import pl.jurassic.roger.data.BreakTime

interface TimerConfiguration {

    var isRunning: Boolean
    var initialize: Boolean

    var startJobTime: Long
    var totalJobTimeThatPass: Long

    var breakTimesList: ArrayList<BreakTime>
}

enum class BreakType(@ColorRes val breakColorRes: Int) {
    LUNCH(R.color.break_lunch),
    SMOKING(R.color.break_smoking),
    OTHER(R.color.break_other)
}