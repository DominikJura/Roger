package pl.jurassic.roger.feature.common.ui.view

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.FrameLayout
import pl.jurassic.roger.R
import pl.jurassic.roger.getColor
import pl.jurassic.roger.getDrawable
import pl.jurassic.roger.getString
import kotlinx.android.synthetic.main.view_break_item.view.break_item_image as breakImageView
import kotlinx.android.synthetic.main.view_break_item.view.break_item_image_container as container
import kotlinx.android.synthetic.main.view_break_item.view.break_item_time as breakTimeTextView

class BreakItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    @DrawableRes
    var breakImageDrawable: Int? = null
        set(drawableId) {
            drawableId?.let { breakImageView.setImageDrawable(getDrawable(it)) }
        }

    @DrawableRes
    var breakBackgroundResource: Int? = null
        set(backgroundRes) {
            backgroundRes?.let { container.setBackgroundResource(it) }
        }

    @ColorInt
    var breakImageColor: Int? = null
        set(colorSelector) {
            colorSelector?.let { breakImageView.imageTintList = ContextCompat.getColorStateList(context, it) }
        }

    @ColorInt
    var breakTimeTextColor: Int? = null
        set(color) {
            color?.let { breakTimeTextView.setTextColor(it) }
        }

    var breakTimeText: String = getString(R.string.timer_zero)
        set(timeText) {
            breakTimeTextView.text = timeText
        }

    init {
        inflateLayout()
        initTypedArray(attrs)
    }

    private fun inflateLayout() {
        inflate(context, R.layout.view_break_item, this)
    }

    private fun initTypedArray(attrs: AttributeSet?) {
        val typeArray = context.theme?.obtainStyledAttributes(attrs, R.styleable.BreakItem, 0, 0)
        typeArray?.let {
            breakTimeTextColor = it.getColor(R.styleable.BreakItem_time_text_color, getColor(R.color.break_smoking))
            breakImageDrawable = it.getResourceId(R.styleable.BreakItem_drawable_id, R.drawable.ic_arrow_back)
            breakBackgroundResource = it.getResourceId(
                R.styleable.BreakItem_background_selector,
                R.drawable.break_smoking_background_selector
            )
            breakImageColor = it.getResourceId(
                R.styleable.BreakItem_image_tint_selector,
                R.color.break_smoking_image_tint_selector
            )
            it.recycle()
        }
    }

    override fun setSelected(selected: Boolean) {
        breakImageView.isSelected = selected
        super.setSelected(selected)
    }
}