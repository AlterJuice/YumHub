package com.alterjuice.chat_assistant.ui.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ConversationComponent(
    modifier: Modifier,
    message: String
) {

    Text(
        text = message,
        modifier = Modifier.wrapContentSize(),
        fontSize = 25.sp
    )

}

@Composable
@Preview
private fun ConversationComponentPreview() {
    ConversationComponent(
        modifier = Modifier,
        message = "Test"
    )
}