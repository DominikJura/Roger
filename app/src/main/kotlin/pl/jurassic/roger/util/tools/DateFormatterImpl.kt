package pl.jurassic.roger.util.tools

import org.joda.time.DateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class DateFormatterImpl : DateFormatter {

    companion object {
        const val JOB_TIME_FORMAT: String = "%d:%02d:%02d"

        @JvmStatic
        val WORK_DATE_FORMAT: DateTimeFormatter = DateTimeFormat.forPattern("MMMM,\ndd yyyy")
    }

    override fun parseTime(timestamp: Long): String {
        val localTime = LocalTime(timestamp)
        return String.format(
                JOB_TIME_FORMAT,
                localTime.hourOfDay,
                localTime.minuteOfHour,
                localTime.secondOfMinute
        )
    }

    override fun parseDate(dateTime: DateTime): String =
        WORK_DATE_FORMAT.print(dateTime)
}