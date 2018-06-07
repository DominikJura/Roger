package pl.jurassic.roger

import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View

typealias OnClickedListener = () -> Unit

fun View.getString(@StringRes id: Int): String = context.getString(id)

fun View.getColor(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)

fun View.getDrawable(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(context, id)

fun RecyclerView.ViewHolder.getColor(@ColorRes id: Int): Int = itemView.getColor(id)

fun RecyclerView.ViewHolder.getDimen(@DimenRes id: Int): Int = itemView.getDimen(id)

fun View.getDimen(@DimenRes id: Int): Int = resources.getDimensionPixelOffset(id)

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}