package pl.jurassic.roger.data

import org.joda.time.DateTime

data class WorkTime(
    val jobTime: Long,
    val breakTime: ArrayList<BreakTime>,
    val dateTime: DateTime
)