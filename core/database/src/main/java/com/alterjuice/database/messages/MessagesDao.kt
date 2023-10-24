package com.alterjuice.database.messages

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MessagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMessage(message: MessageEntityDB)

    @Query("SELECT * FROM Messages LIMIT 50")
    suspend fun getMessages(): List<MessageEntityDB>

    @Query("SELECT * FROM Messages LIMIT 50")
    fun getMessagesFlow(): Flow<List<MessageEntityDB>>

    @Query("SELECT * FROM Messages LIMIT :count OFFSET :offset")
    suspend fun getMessages(offset: Int, count: Int): List<MessageEntityDB>
}