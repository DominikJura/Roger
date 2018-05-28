package pl.jurassic.roger.feature.summary.ui.view

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import pl.jurassic.roger.data.ui.BreakBarData
import pl.jurassic.roger.getColor
import pl.jurassic.roger.util.timer.BreakType

class SummaryChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BarChart(context, attrs, defStyleAttr) {


    companion object {
        //TODO move all magic numbers

        @JvmStatic
        val CHART_DATE_FORMAT: DateTimeFormatter = DateTimeFormat.forPattern("MMM dd")
    }

    init {
        initXAxis()
        initLeftAxis()
        setLegend()
        disableDescription()

        axisRight.isEnabled = false
    }

    private fun setLegend() = with(legend) {
        form = Legend.LegendForm.CIRCLE
        xEntrySpace = 20f
    }

    private fun initLeftAxis() = with(axisLeft) {
        axisMinimum = 0f
        setValueFormatter { value, _ ->
            val dateTime = LocalTime(value.toLong(), DateTimeZone.UTC)
            return@setValueFormatter "${dateTime.minuteOfHour}m ${dateTime.secondOfMinute}s"
        }
    }

    private fun initXAxis() = with(xAxis) {
        position = XAxis.XAxisPosition.BOTTOM
        setCenterAxisLabels(true)
        xOffset = 0f
        granularity = 1f
        setValueFormatter { value, _ -> CHART_DATE_FORMAT.print(DateTime().withDayOfYear(value.toInt())) }
    }

    private fun disableDescription() {
        description.isEnabled = false
    }


    fun setBarData(barDataList: List<BreakBarData>) {

        val smokingList = arrayListOf<BarEntry>()
        val lunchList = arrayListOf<BarEntry>()
        val otherList = arrayListOf<BarEntry>()


        barDataList.forEach {
            when(it.breakType) {
                BreakType.LUNCH -> lunchList.add(BarEntry(it.dateTime.dayOfYear.toFloat(), it.totalBreakTime.toFloat()))
                BreakType.SMOKING -> smokingList.add(BarEntry(it.dateTime.dayOfYear.toFloat(), it.totalBreakTime.toFloat()))
                BreakType.OTHER -> otherList.add(BarEntry(it.dateTime.dayOfYear.toFloat(), it.totalBreakTime.toFloat()))
            }
        }

        val smokingDataSet = BarDataSet(smokingList, BreakType.SMOKING.name)
        val lunchDataSet = BarDataSet(lunchList, BreakType.LUNCH.name)
        val otherDataSet = BarDataSet(otherList, BreakType.OTHER.name)

        smokingDataSet.color = getColor(BreakType.SMOKING.breakColorRes)
        lunchDataSet.color = getColor(BreakType.LUNCH.breakColorRes)
        otherDataSet.color = getColor(BreakType.OTHER.breakColorRes)

        val groupSpace = 0.04f
        val barSpace = 0.02f
        val barWidth = 0.30f

        data = BarData(smokingDataSet, lunchDataSet, otherDataSet)
        data.barWidth = barWidth // set the width of each bar
        data.setDrawValues(false)

        val minElement = barDataList
            .minBy { it.dateTime.dayOfYear }
            ?.let { it.dateTime.dayOfYear.toFloat() } ?: 0f

        groupBars(minElement, groupSpace, barSpace)

        xAxis.axisMinimum = minElement
        xAxis.axisMaximum = minElement + 5

        invalidate()
    }
}