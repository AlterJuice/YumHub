package com.alterjuice.domain.repository

import com.alterjuice.domain.model.messages.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MessagesRepository {
    suspend fun sendMessage(message: Message): Result<Message>
    suspend fun saveMessage(message: Message)
    suspend fun getMessages(): Result<List<Message>>

    fun getMessagesFlow(): Flow<List<Message>>
}

interface MessagesRepositoryExtended: MessagesRepository {
    val waitingForResponse: StateFlow<Boolean>
}