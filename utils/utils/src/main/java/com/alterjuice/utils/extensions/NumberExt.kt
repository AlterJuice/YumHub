package com.alterjuice.utils.extensions

import java.math.RoundingMode
import kotlin.math.abs


fun Long.roundTo(digits: Int): Long {
    return toBigDecimal().setScale(digits, RoundingMode.HALF_EVEN).toLong()
}
fun Double.roundTo(digits: Int): Double {
    return toBigDecimal().setScale(digits, RoundingMode.HALF_EVEN).toDouble()
}
fun Float.roundTo(digits: Int): Float {
    return toBigDecimal().setScale(digits, RoundingMode.HALF_EVEN).toFloat()
}

fun Float.gracefulRound(digits: Int = 1): Number {
    if (this == toBigDecimal().setScale(0, RoundingMode.HALF_EVEN).toFloat()) {
        return this.toInt()
    } else {
        return roundTo(digits)
    }
}

fun Double.gracefulRound(digits: Int = 1): Number {
    if (this == toBigDecimal().setScale(0, RoundingMode.HALF_EVEN).toDouble()) {
        return this.toInt()
    } else {
        return roundTo(digits)
    }
}



/** Returns the next value dividable by divBy;
 *
 * (999L).getNextDividable(5); = 1000
 * (1000L).getNextDividable(5); = 1000
 * (1001L).getNextDividable(5); = 1005
 * (1004L).getNextDividable(5); = 1005
 * (-999L).getNextDividable(5); = -995
 * (-995L).getNextDividable(5); = -995
 * (-1L).getNextDividable(5); = 0
 * (1L).getNextDividable(5); = 5
 * (0L).getNextDividable(5); = 0
 * */
fun Long.getNextDividable(divBy: Long): Long {
    val tail = abs(this % divBy)
    return if (this < 0) {
        this + tail
    } else {
        this + (divBy - tail)
    }
}