package com.alterjuice.utils.extensions

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

fun Int.divMod(b: Int) = Pair(this / b, this % b)
fun Long.divMod(b: Long) = Pair(this / b, this % b)

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

fun <T> List<T>.contains(condition: (T) -> Boolean): Boolean {
    return this.firstOrNull(condition) != null
}