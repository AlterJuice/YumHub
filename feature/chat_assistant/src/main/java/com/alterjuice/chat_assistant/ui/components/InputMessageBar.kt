package com.alterjuice.chat_assistant.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputMessageBar(
    modifier: Modifier,
    sendMessage: (String) -> Unit
) {
    var msg by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .then(modifier)
    ) {
        TextField(
            modifier = Modifier,// .weight(1f),
            value = msg,
            onValueChange = {
                msg = it
            },
            placeholder = {
                Text("Ask a question...")
            }
        )
        SendMessageButton(
            modifier = Modifier
                .width(100.dp)
                .height(40.dp)
                // .aspectRatio(1f)
                .background(Color.Red, CircleShape)
                .clickable {
                    sendMessage(msg)
                    msg = ""
                }
        )
    }
}

@Composable
@Preview
private fun InputMessageBarPreview() {
    InputMessageBar(
        modifier = Modifier,
        sendMessage = { }
    )
}