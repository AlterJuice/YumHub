package com.alterjuice.database.messages

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alterjuice.domain.model.messages.MessageAuthor


@Entity(tableName = "Messages")
data class MessageEntityDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "message_id") val messageID: Long,
    @ColumnInfo(name = "author") val author: MessageAuthor,
    @ColumnInfo(name = "time") val messageTimeStamp: String,
    @ColumnInfo(name = "text") val messageText: String,
)