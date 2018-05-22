package pl.jurassic.roger.data

import org.joda.time.DateTime

data class WorkTime(
        val startJobTime: DateTime,
        val endDateTime: DateTime,
        val jobTime: Long,
        val breakTime: ArrayList<BreakTime>

)