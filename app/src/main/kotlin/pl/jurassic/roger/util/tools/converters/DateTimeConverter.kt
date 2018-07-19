package pl.jurassic.roger.util.tools.converters

import android.arch.persistence.room.TypeConverter
import org.joda.time.DateTime

class DateTimeConverter {

    @TypeConverter
    fun fromTimestamp(timestamp: Long): DateTime = DateTime(timestamp)

    @TypeConverter
    fun dateToTimestamp(date: DateTime): Long = date.millis
}