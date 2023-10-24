package com.alterjuice.chat_assistant.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SendMessageButton(
    modifier: Modifier
) {
    Box(modifier)
}

@Composable
@Preview
private fun SendMessageButtonPreview() {
    SendMessageButton(
        modifier = Modifier
    )
}