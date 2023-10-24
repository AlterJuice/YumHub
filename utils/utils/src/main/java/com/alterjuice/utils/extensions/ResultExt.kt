package com.alterjuice.utils.extensions

fun <T> Result<T>.onCompletion(block: () -> Unit)
    = this.onSuccess { block() }.onFailure { block() }