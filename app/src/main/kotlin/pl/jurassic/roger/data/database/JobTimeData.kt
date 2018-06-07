package pl.jurassic.roger.data.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import org.joda.time.DateTime
import pl.jurassic.roger.util.tools.converters.DateTimeConverter
import pl.jurassic.roger.util.tools.converters.DateTimeKeyConverter

@Entity(tableName = "job_time" )
data class JobTimeData(
    @PrimaryKey @field:TypeConverters(DateTimeKeyConverter::class) val dateTimeKey: DateTime,
    @field:TypeConverters(DateTimeConverter::class) val startTime: DateTime,
    @field:TypeConverters(DateTimeConverter::class) val endTime: DateTime,
    val jobTime: Long
)