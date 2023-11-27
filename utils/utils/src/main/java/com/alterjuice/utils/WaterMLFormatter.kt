package com.alterjuice.utils

import com.alterjuice.utils.extensions.divMod
import com.alterjuice.utils.extensions.roundTo

object WaterMLFormatter {
    fun mlToLiters(waterMls: Long): String {
        val (liters, mls) = waterMls.divMod(1000)
        if (liters != 0L && mls != 0L) {
            return "${waterMls.div(1000.0).roundTo(1)}L"
        }
        val result = buildList<String> {
            if (liters != 0L) add("${liters}L")
            if (liters == 0L) add("${mls}ML")
        }.joinToString(" ").ifEmpty { "-" }

        return result
    }
    fun mlToLiters(waterMls: Int): String {
        return mlToLiters(waterMls.toLong())
    }
}