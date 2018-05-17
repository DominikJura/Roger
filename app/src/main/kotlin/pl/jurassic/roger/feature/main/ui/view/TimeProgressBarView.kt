package pl.jurassic.roger.feature.main.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import pl.jurassic.roger.R
import pl.jurassic.roger.data.ui.ProgressAngles
import pl.jurassic.roger.getColor

class TimeProgressBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val RING_STROKE_WITH = 2f
        private const val RING_INDICATOR_OF_HOUR_SWEEP_ANGLE = 0.5f
        private const val INNER_CIRCLE_SHADOW_RADIUS = 10f
        private const val STARTING_ANGLE = 270f
    }

    private lateinit var progressAngles: ProgressAngles

    private val outerCircleRectF = RectF()
    private val innerCircleRectF = RectF()

    private val innerCircleWhitePaint = Paint()
    private val innerCircleShadowPaint = Paint()
    private val ringBluePaint = Paint()
    private val ringIndicatorPaint = Paint()
    private val ringStrokePaint = Paint()

    private val breakRingPaint = Paint()

    init {
        initPaints()
    }

    private fun initPaints() {
        innerCircleWhitePaint.color = getColor(R.color.pale_grey)
        innerCircleWhitePaint.style = Paint.Style.FILL
        innerCircleWhitePaint.isAntiAlias = true

        innerCircleShadowPaint.color = getColor(R.color.white)
        innerCircleShadowPaint.setShadowLayer(INNER_CIRCLE_SHADOW_RADIUS, 0f, 0f, getColor(R.color.black16))
        innerCircleShadowPaint.isAntiAlias = true

        ringBluePaint.color = getColor(R.color.lightish_blue)
        ringBluePaint.isAntiAlias = true

        ringIndicatorPaint.isAntiAlias = true

        ringStrokePaint.color = getColor(R.color.lightish_blue)
        ringStrokePaint.style = Paint.Style.STROKE
        ringStrokePaint.strokeWidth = RING_STROKE_WITH
        ringStrokePaint.isAntiAlias = true

        breakRingPaint.isAntiAlias = true

        setLayerType(LAYER_TYPE_SOFTWARE, innerCircleShadowPaint)
    }

    fun setProgressAngle(progressAngles: ProgressAngles) {
        this.progressAngles = progressAngles
        invalidate()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //TODO clear that code
        super.onLayout(changed, left, top, right, bottom)
        val someValue = (right - left) / 6
        outerCircleRectF.set(0f, 0f, (right - left).toFloat(), (bottom - top).toFloat())
        innerCircleRectF.set(0f + someValue, 0f + someValue, (right - left - someValue).toFloat(), (bottom - top - someValue).toFloat())
    }

    override fun onDraw(canvas: Canvas) = with(canvas) {
        super.onDraw(this)

        drawOval(outerCircleRectF, innerCircleWhitePaint)
        drawOval(outerCircleRectF, ringStrokePaint)

        drawArc(outerCircleRectF, STARTING_ANGLE, progressAngles.jobProgressAngle, true, ringBluePaint)

        drawBreakProgressAngles(this)

        drawRingHourIndicators(this)

        drawOval(innerCircleRectF, innerCircleShadowPaint)
    }

    private fun drawBreakProgressAngles(canvas: Canvas) = with(canvas) {
        progressAngles.progressBreakAngles.forEach {
            breakRingPaint.color = getColor(it.arcColor)
            drawArc(outerCircleRectF, STARTING_ANGLE + it.startAngle, it.sweepAngle, true, breakRingPaint)
        }
    }

    private fun drawRingHourIndicators(canvas: Canvas) = with(canvas) {
        progressAngles.hourProgressIndicators.forEach {
            ringIndicatorPaint.color = getColor(it.colorRes)

            drawArc(
                outerCircleRectF,
                STARTING_ANGLE + it.sweepAngle,
                RING_INDICATOR_OF_HOUR_SWEEP_ANGLE,
                true,
                ringIndicatorPaint
            )
        }
    }
}