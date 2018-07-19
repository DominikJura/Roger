package pl.jurassic.roger.data.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Index
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.PrimaryKey
import org.joda.time.DateTime
import pl.jurassic.roger.data.BreakType
import pl.jurassic.roger.util.tools.converters.BreakTypeConverter
import pl.jurassic.roger.util.tools.converters.DateTimeConverter
import pl.jurassic.roger.util.tools.converters.DateTimeKeyConverter

@Entity(tableName = "break_time",
        indices = [(Index("dateTimeForeignKey"))],
        foreignKeys = [(ForeignKey(
                entity = JobTimeData::class,
                parentColumns = arrayOf("dateTimeKey"),
                childColumns = arrayOf("dateTimeForeignKey"),
                onDelete = CASCADE
        ))])
class BreakTimeData(
        @field:TypeConverters(DateTimeKeyConverter::class) val dateTimeForeignKey: DateTime,
        @field:TypeConverters(BreakTypeConverter::class) val breakType: BreakType,
        @field:TypeConverters(DateTimeConverter::class) val startDateTime: DateTime,
        @field:TypeConverters(DateTimeConverter::class) val stopDateTime: DateTime
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}