package pl.jurassic.roger.feature.summary.ui.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import pl.jurassic.roger.R
import kotlinx.android.synthetic.main.view_summary_time.view.summary_time_date_time as dateTimeTextView
import kotlinx.android.synthetic.main.view_summary_time.view.summary_time_title_text as titleTextView

class SummaryTimeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var dateTimeText: CharSequence
        get() = dateTimeTextView.text
        set(text) {
            dateTimeTextView.text = text
        }

    private var titleTimeText: CharSequence
        get() = titleTextView.text
        set(text) {
            titleTextView.text = text
        }

    init {
        inflate(context, R.layout.view_summary_time, this)
        initTypeArray(attrs)
    }

    private fun initTypeArray(attrs: AttributeSet?) {
        val typeArray = context.theme?.obtainStyledAttributes(attrs, R.styleable.SummaryTimeView, 0, 0)
        typeArray?.let {
            titleTimeText = it.getString(R.styleable.SummaryTimeView_title_text)
            it.recycle()
        }
    }
}