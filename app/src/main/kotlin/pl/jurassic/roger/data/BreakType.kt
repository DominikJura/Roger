package pl.jurassic.roger.data

import android.support.annotation.ColorRes
import pl.jurassic.roger.R

enum class BreakType(@ColorRes val breakColorRes: Int) {
    SMOKING(R.color.break_smoking),
    LUNCH(R.color.break_lunch),
    OTHER(R.color.break_other)
}