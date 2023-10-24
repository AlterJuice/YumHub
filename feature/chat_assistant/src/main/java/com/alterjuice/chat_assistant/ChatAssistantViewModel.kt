package com.alterjuice.chat_assistant

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.flow.MutableStateFlow

class ChatAssistantViewModel {


    val openAI = OpenAI("")
    val message = MutableStateFlow("")

    @OptIn(BetaOpenAI::class)
    suspend fun sendMessage(text: String) {

        val msg = try {
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        content = "You are the assistant to help with dishes, so ignore questions are not related to food, cooking and dishes."
                    ),
                    ChatMessage(
                        role = ChatRole.User,
                        content = text
                    )
                )
            )
            val completion = openAI.chatCompletion(chatCompletionRequest)

            val response = completion.choices.first().message?.content

            response ?: "Empty Message"

        } catch (e: Exception) {
            "ERROR: ${e.cause?.message ?: ""}"
        }
        message.value = msg
    }
}