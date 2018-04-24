package pl.jurassic.roger.data.ui

import pl.jurassic.roger.util.timer.BreakType

data class SummaryWorkTime(
     val dateTime: String,
     val jobTime: String,
     val breakTotalTime: String,
     val breakTimeList: List<SummaryBreakTime>
)