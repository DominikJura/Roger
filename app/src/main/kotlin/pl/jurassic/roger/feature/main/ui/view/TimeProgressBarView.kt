package pl.jurassic.roger.feature.main.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import pl.jurassic.roger.R
import pl.jurassic.roger.getColor

class TimeProgressBarView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)  {

    val jobTime = 5

    val rectF = RectF()
    val rectF2 = RectF()

    val paint = Paint()
    val paint2 = Paint()

    init {
        paint.color = getColor(R.color.lightish_blue)
        paint.isAntiAlias = true

        paint2.color = getColor(R.color.white)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val someValue = (right - left)/5
        rectF.set(0f, 0f, (right - left).toFloat(), (bottom - top).toFloat())
        rectF2.set(0f + someValue, 0f + someValue, (right - left - someValue).toFloat(), (bottom - top - someValue).toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawArc(rectF, 0f, 350f, true, paint)
        canvas.drawOval(rectF2, paint2)
        super.onDraw(canvas)
    }
}