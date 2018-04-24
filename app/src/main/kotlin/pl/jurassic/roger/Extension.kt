package pl.jurassic.roger

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.View

typealias OnClickedListener = () -> Unit

fun View.getString(id: Int): String = context.getString(id)

fun View.getColor(id: Int): Int = ContextCompat.getColor(context, id)

fun View.getDrawable(id: Int): Drawable? = ContextCompat.getDrawable(context, id)