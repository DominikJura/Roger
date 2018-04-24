package pl.jurassic.roger.feature.common.ui.view

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
import pl.jurassic.roger.R
import pl.jurassic.roger.getDrawable
import kotlinx.android.synthetic.main.view_break_item.view.break_item_image as breakImageView

class BreakItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr)  {

    @DrawableRes
    var breakImageDrawable: Int? = null
        set(drawableId) {
            drawableId?.let { breakImageView.setImageDrawable(getDrawable(it)) }
        }

    @DrawableRes
    var breakBackgroundResource: Int? = null
        set(backgroundRes) {
            backgroundRes?.let { setBackgroundResource(it) }
        }

    @ColorInt
    var breakImageColor: Int? = null
        set(colorSelector) {
            colorSelector?.let {
                breakImageView.imageTintList = ContextCompat.getColorStateList(context, it)
            }
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
            breakImageDrawable = it.getResourceId(
                    R.styleable.BreakItem_drawable_id,
                    R.drawable.ic_arrow_back
            )
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