package pl.jurassic.roger.data.ui

data class SummaryWorkTime(
    val dateTime: String,
    val jobTime: String,
    val breakTotalTime: String,
    val breakTimeList: List<SummaryBreakTime>
)