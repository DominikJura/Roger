package pl.jurassic.roger.util.tools

import org.joda.time.DateTime

interface DateFormatter {

    fun parseTime(timestamp: Long): String
    fun parseDate(dateTime: DateTime): String
}