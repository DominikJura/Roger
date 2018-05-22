package pl.jurassic.roger.util.tools.converters

import android.arch.persistence.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class DateTimeKeyConverter {

    @TypeConverter
    fun fromTimestamp(timestamp: String): DateTime = DateTime.parse(timestamp, DateTimeFormat.shortDate())

    @TypeConverter
    fun dateToTimestamp(date: DateTime): String = date.toString(DateTimeFormat.shortDate())
}