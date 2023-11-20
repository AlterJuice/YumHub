package com.alterjuice.repository.storage

fun interface StorageCleaner {
    suspend fun clearData()
}