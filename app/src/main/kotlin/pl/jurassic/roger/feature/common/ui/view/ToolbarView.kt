package pl.jurassic.roger.feature.common.ui.view

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import pl.jurassic.roger.OnClickedListener
import pl.jurassic.roger.R
import kotlinx.android.synthetic.main.view_toolbar.view.toolbar_icon_stats as statisticsFrameLayout
import kotlinx.android.synthetic.main.view_toolbar.view.toolbar_navigation_back as navigationBackImageView
import kotlinx.android.synthetic.main.view_toolbar.view.toolbar_title as titleTextView

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.toolbarStyle
) : Toolbar(context, attrs, defStyleAttr) {

    companion object {
        private const val colorUndefined = -1
    }

    var text: String
        set(text) {
            titleTextView.text = title
        }
        get() = titleTextView.text.toString()

    var textColor: Int
        set(color) {
            titleTextView.setTextColor(color)
        }
        get() = titleTextView.currentTextColor

    var navigationBackColor: Int = colorUndefined
        set(color) {
            navigationBackImageView.setColorFilter(color)
        }

    var navigationBackClickedListener: OnClickedListener? = null
    var statisticsClickedListener: OnClickedListener? = null

    init {
        inflate(context, R.layout.view_toolbar, this)
    }

    private fun initOnClickListeners() {
        statisticsFrameLayout.setOnClickListener { statisticsClickedListener?.invoke() }
        navigationBackImageView.setOnClickListener { navigationBackClickedListener?.invoke() }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initOnClickListeners()
        setPadding(0, 0, 0, 0)
        setContentInsetsAbsolute(0, 0)
    }

    fun changeNavigationBackIcon(@DrawableRes drawableId: Int) {
        navigationBackImageView.setImageResource(drawableId)
        navigationBackImageView.setColorFilter(navigationBackColor)
    }
}