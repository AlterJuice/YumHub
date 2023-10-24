package com.alterjuice.repository.repository

import com.alterjuice.domain.model.messages.Message
import com.alterjuice.domain.repository.MessagesRepository
import com.alterjuice.domain.repository.MessagesRepositoryExtended
import com.alterjuice.repository.datasources.MessagesLocalDataSource
import com.alterjuice.repository.datasources.MessagesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class MessagesRepositoryImpl(
    private val local: MessagesLocalDataSource,
    private val remote: MessagesRemoteDataSource,
): MessagesRepositoryExtended {
    override val waitingForResponse = MutableStateFlow<Boolean>(false)

    override suspend fun getMessages() = runCatching<List<Message>> {
        local.getMessages()
    }

    override suspend fun sendMessage(message: Message) = runCatching<Message> {
        saveMessage(message)
        val deferredMessage = remote.sendMessage(message)
        waitingForResponse.value = true
        deferredMessage.await().onCompletion {
            waitingForResponse.value = false
        }.onSuccess { saveMessage(it) }.getOrThrow()
    }

    override fun getMessagesFlow(): Flow<List<Message>> {
        return local.getMessagesFlow()
    }

    override suspend fun saveMessage(message: Message) {
        local.saveMessage(message)
    }
}

fun <T> Result<T>.onCompletion(block: () -> Unit)
    = this.onSuccess { block() }.onFailure { block() }