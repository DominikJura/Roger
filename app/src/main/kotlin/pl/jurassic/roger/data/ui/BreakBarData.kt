package pl.jurassic.roger.data.ui

import org.joda.time.DateTime
import pl.jurassic.roger.data.BreakType

data class BreakBarData(val breakType: BreakType, val dateTime: DateTime, val totalBreakTime: Long)