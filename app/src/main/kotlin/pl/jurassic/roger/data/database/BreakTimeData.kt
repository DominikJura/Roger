package pl.jurassic.roger.data.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import org.joda.time.DateTime
import pl.jurassic.roger.util.timer.BreakType
import pl.jurassic.roger.util.tools.converters.BreakTypeConverter
import pl.jurassic.roger.util.tools.converters.DateTimeKeyConverter

@Entity(tableName = "break_time")
data class BreakTimeData(
        @TypeConverters(DateTimeKeyConverter::class) val dateTimeForeignKey: DateTime,
        @TypeConverters(BreakTypeConverter::class) val breakType: BreakType,
        val startTimestamp: Long,
        var stopTimestamp: Long
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}