package pl.jurassic.roger

import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View

typealias OnClickedListener = () -> Unit

fun View.getString(id: Int): String = context.getString(id)

fun View.getColor(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)

fun View.getDrawable(id: Int): Drawable? = ContextCompat.getDrawable(context, id)

fun RecyclerView.ViewHolder.getColor(@ColorRes id: Int): Int =
    itemView.getColor(id)

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum: Long = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}