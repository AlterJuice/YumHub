package com.alterjuice.chat_assistant.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alterjuice.chat_assistant.ChatAssistantViewModel
import com.alterjuice.chat_assistant.ui.components.ConversationActionBar
import com.alterjuice.chat_assistant.ui.components.ConversationComponent
import com.alterjuice.chat_assistant.ui.components.InputMessageBar
import com.alterjuice.compose_utils.ui.extensions.rememberCreateRef
import kotlinx.coroutines.launch

@Composable
fun ChatAssistantScreen(
    modifier: Modifier
) {
    val vm = remember {
        ChatAssistantViewModel()
    }

    val scope = rememberCoroutineScope()
    ConstraintLayout(
        modifier = Modifier.then(modifier)
    ) {
        val inputMessageBarRef = rememberCreateRef()
        val conversationRef = rememberCreateRef()
        val conversationActionBarRef = rememberCreateRef()
        ConversationActionBar(
            modifier = Modifier
                .constrainAs(conversationActionBarRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(60.dp),

        )
        val msg by vm.message.collectAsState()
        ConversationComponent(
            modifier = Modifier.constrainAs(conversationRef) {
                top.linkTo(conversationActionBarRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(inputMessageBarRef.top)
            },
            message = msg
        )

        InputMessageBar(
            modifier = Modifier
                .constrainAs(inputMessageBarRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth()
                .height(50.dp),
            sendMessage = {
                scope.launch {
                    vm.sendMessage(it)
                }
            }
        )
    }

}

@Composable
@Preview
private fun ChatAssistantScreenPreview() {
    ChatAssistantScreen(
        modifier = Modifier.fillMaxSize()
    )
}