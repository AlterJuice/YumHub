package com.alterjuice.android_utils

import android.content.Context
import androidx.annotation.StringRes

/**
 * A String representation to store both text or resourceID with arguments.
 * */
sealed interface Str {
    class Res(@StringRes val id: Int, vararg val args: Any): Str
    class Text(val text: String): Str
    class Lambda(val block: () -> String): Str
    companion object {
        operator fun invoke(block: () -> String) = Lambda(block)
        operator fun invoke(text: String) = Text(text)
        operator fun invoke(@StringRes id: Int, vararg args: Any) = Res(id, *args)
    }
}

fun Str.get(context: Context): String = when(this) {
    is Str.Res -> context.getString(this.id, *this.args)
    is Str.Text -> this.text
    is Str.Lambda -> this.block.invoke()
}