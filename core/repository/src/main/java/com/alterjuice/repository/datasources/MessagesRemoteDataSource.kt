package com.alterjuice.repository.datasources

import com.alterjuice.domain.model.messages.Message
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class MessagesRemoteDataSource(
    private val api: Any,
) {

    /** Sends message to API and gets the response in Message representation
     * */
    suspend fun sendMessage(message: Message): Deferred<Result<Message>> {
        return coroutineScope {
            async {
                runCatching<Message> {
                    message
                }
            }
        }
    }

}