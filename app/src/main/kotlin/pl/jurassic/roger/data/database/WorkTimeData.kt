package pl.jurassic.roger.data.database

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class WorkTimeData {

    @Embedded
    lateinit var jobTimeData: JobTimeData

    @Relation(parentColumn = "dateTimeKey", entityColumn = "dateTimeForeignKey")
    var breakTimeList: List<BreakTimeData> = ArrayList()
}