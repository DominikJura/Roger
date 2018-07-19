package pl.jurassic.roger.data.ui

data class ProgressAngles(
    val jobProgressAngle: Float,
    val hourProgressIndicators: List<ProgressHourIndicator>,
    val progressBreakAngles: List<BreakProgressAngle>
)