package com.alterjuice.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alterjuice.database.messages.MessageEntityDB
import com.alterjuice.database.messages.MessagesDao


@Database(
    entities = [MessageEntityDB::class],
    version = 1,
    exportSchema = false
)
abstract class YumHubDatabase: RoomDatabase() {
    abstract fun messagesEntities(): MessagesDao
}