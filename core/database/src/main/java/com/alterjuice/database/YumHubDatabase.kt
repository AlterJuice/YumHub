package com.alterjuice.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alterjuice.database.messages.MessageEntityDB
import com.alterjuice.database.messages.MessagesDao


@Database(
    entities = [MessageEntityDB::class],
    version = 1,
    exportSchema = true
)
abstract class YumHubDatabase: RoomDatabase() {
    abstract fun messagesEntities(): MessagesDao
}


fun createYumHubDatabase(application: Application) = Room.databaseBuilder(
    context = application,
    klass = YumHubDatabase::class.java,
    name = "YumHubDatabase"
).build()