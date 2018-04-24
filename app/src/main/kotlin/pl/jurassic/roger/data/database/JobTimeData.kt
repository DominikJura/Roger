package pl.jurassic.roger.data.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import org.joda.time.DateTime
import pl.jurassic.roger.util.tools.converters.DateTimeConverter

@Entity(tableName = "job_time")
data class JobTimeData(
        @PrimaryKey @TypeConverters(DateTimeConverter::class) val dateTime: DateTime,
        val jobTime: Long
)