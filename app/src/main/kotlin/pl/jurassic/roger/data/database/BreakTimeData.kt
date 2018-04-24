package pl.jurassic.roger.data.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import org.joda.time.DateTime
import pl.jurassic.roger.util.timer.BreakType
import pl.jurassic.roger.util.tools.converters.BreakTypeConverter
import pl.jurassic.roger.util.tools.converters.DateTimeConverter

@Entity(tableName = "break_time")
data class BreakTimeData(
        @TypeConverters(BreakTypeConverter::class) val breakType: BreakType,
        @TypeConverters(DateTimeConverter::class) val dateTime: DateTime,
        val breakTime: Long

) {
    @PrimaryKey var id: String = "${breakType.name}-${dateTime.dayOfYear()}"
}