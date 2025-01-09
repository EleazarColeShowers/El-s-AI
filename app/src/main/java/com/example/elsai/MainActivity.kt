package com.example.elsai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.elsai.ui.theme.ElsAITheme

class MainActivity : ComponentActivity() {
    @RequiresApi(35)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElsAITheme {
                val viewModel: ChatViewModel = viewModel()
                SinglePageWithTextField(viewModel = viewModel)
            }
        }
    }
}

@RequiresApi(35)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SinglePageWithTextField(viewModel: ChatViewModel) {
    val focusManager = LocalFocusManager.current
    val (userMessage, setUserMessage) = remember { mutableStateOf("") }
    val messageList = viewModel.messageList // Observe messageList from ViewModel

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                messageList.forEach { message ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = if (message.role == "user") Arrangement.End else Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (message.role == "user") Color.DarkGray else Color.Gray,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(12.dp)
                                .widthIn(max = 300.dp)
                        ) {
                            Text(
                                text = message.message,
                                color = Color.White,
                                style = TextStyle(fontSize = 14.sp)
                            )
                        }
                    }
                }
            }

            // TextField and Send Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .background(
                            color = Color(0xFF424242),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(15.dp)
                        )
                ) {
                    TextField(
                        value = userMessage,
                        onValueChange = setUserMessage,
                        placeholder = {
                            Text(
                                text = "Type a message...",
                                color = Color.White.copy(alpha = 0.6f),
                                style = TextStyle(fontSize = 14.sp)
                            )
                        },
                        singleLine = false,
                        maxLines = 8,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .wrapContentHeight()
                            .verticalScroll(rememberScrollState()),
                        shape = RoundedCornerShape(15.dp),
                        textStyle = TextStyle(fontSize = 14.sp, color = Color.White),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }

                Column(
                    modifier = Modifier
                        .height(48.dp)
                        .width(80.dp)
                        .background(
                            color = Color(0xFF424242),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable {
                            focusManager.clearFocus()
                            if (userMessage.isNotBlank()) {
                                viewModel.sendMessage(userMessage)
                                setUserMessage("") // Clear input field after sending
                            }
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Send",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@RequiresApi(35)
@Preview(showBackground = true)
@Composable
fun SinglePageWithTextFieldPreview() {
    val dummyViewModel = ChatViewModel()
    ElsAITheme {
        SinglePageWithTextField(viewModel = dummyViewModel)
    }
}
