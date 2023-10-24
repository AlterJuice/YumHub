package com.alterjuice.repository.datasources

import com.alterjuice.database.messages.MessageEntityDB
import com.alterjuice.database.messages.MessagesDao
import com.alterjuice.domain.model.messages.Message
import com.alterjuice.repository.mappers.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessagesLocalDataSource(
    private val messagesDB: MessagesDao
) {
    fun getMessagesFlow(): Flow<List<Message>> {
        return messagesDB.getMessagesFlow().map { it.toDomain() }
    }
    suspend fun getMessages(): List<Message> {
        return messagesDB.getMessages().toDomain()
    }

    suspend fun saveMessage(message: Message) {
        val messageDB = MessageEntityDB(
            messageID = 0,
            author = message.author,
            messageTimeStamp = message.timeStamp.toString(),
            messageText = message.text
        )
        saveMessage(messageDB)
    }

    private suspend fun saveMessage(message: MessageEntityDB) {
        messagesDB.saveMessage(message)
    }
}