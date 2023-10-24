package com.alterjuice.domain.model.messages

import java.time.LocalDateTime

data class Message(
    val author: MessageAuthor,
    val text: String,
    val timeStamp: LocalDateTime
)