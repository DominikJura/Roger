package pl.jurassic.roger.util.tools.converters

import android.arch.persistence.room.TypeConverter
import pl.jurassic.roger.util.timer.BreakType

class BreakTypeConverter {

    @TypeConverter
    fun toBreakType(type: Int) =
            when (type) {
                BreakType.SMOKING.ordinal -> BreakType.SMOKING
                BreakType.LUNCH.ordinal -> BreakType.LUNCH
                BreakType.OTHER.ordinal -> BreakType.OTHER
                else -> throw NoSuchElementException()
            }

    @TypeConverter
    fun toInteger(breakType: BreakType) =
            breakType.ordinal
}
