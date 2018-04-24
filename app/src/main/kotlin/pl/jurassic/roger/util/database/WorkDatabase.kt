package pl.jurassic.roger.util.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import pl.jurassic.roger.data.database.BreakTimeData
import pl.jurassic.roger.data.database.JobTimeData
import pl.jurassic.roger.util.tools.converters.BreakTypeConverter
import pl.jurassic.roger.util.tools.converters.DateTimeConverter

@Database(entities = [(JobTimeData::class), (BreakTimeData::class)], version = 1, exportSchema = false)
@TypeConverters(value = [(DateTimeConverter::class), (BreakTypeConverter::class)])
abstract class WorkTimeDatabase : RoomDatabase() {

    abstract fun workTimeDao(): WorkTimeDao
}