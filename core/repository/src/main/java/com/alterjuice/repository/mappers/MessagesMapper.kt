package com.alterjuice.repository.mappers

import com.alterjuice.database.messages.MessageEntityDB
import com.alterjuice.domain.model.messages.Message
import java.time.LocalDateTime


fun List<MessageEntityDB>.toDomain() = this.map {
    it.toDomain()
}

fun MessageEntityDB.toDomain() = Message(
    author = this.author,
    text = this.messageText,
    timeStamp = LocalDateTime.parse(this.messageTimeStamp)
)