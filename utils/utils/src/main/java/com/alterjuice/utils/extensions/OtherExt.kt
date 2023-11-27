package com.alterjuice.utils.extensions

fun Int.divMod(b: Int) = Pair(this / b, this % b)
fun Long.divMod(b: Long) = Pair(this / b, this % b)

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }