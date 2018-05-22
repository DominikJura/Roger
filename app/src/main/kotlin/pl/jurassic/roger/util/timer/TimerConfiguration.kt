package pl.jurassic.roger.util.timer

import android.support.annotation.ColorRes
import org.joda.time.DateTime
import pl.jurassic.roger.R
import pl.jurassic.roger.data.BreakTime

interface TimerConfiguration {

    var isRunning: Boolean

    var startJobTime: DateTime
    var totalJobTimeThatPass: Long

    var timeThatPass: Long //todo refactor


    var breakTimesList: ArrayList<BreakTime>
}

enum class BreakType(@ColorRes val breakColorRes: Int) {
    SMOKING(R.color.break_smoking),
    LUNCH(R.color.break_lunch),
    OTHER(R.color.break_other)
}