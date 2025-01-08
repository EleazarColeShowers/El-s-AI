package com.example.elsai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.elsai.ui.theme.ElsAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElsAITheme {
                SinglePageWithTextField()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SinglePageWithTextField() {
    val focusManager = LocalFocusManager.current
    val (message, setMessage) = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TextField with grey background and white text
            TextField(
                value = message,
                onValueChange = setMessage,
                placeholder = {
                    Text(
                        text = "Type a message...",
                        color = Color.White.copy(alpha = 0.6f), // Light white placeholder text
                        style = TextStyle(fontSize = 14.sp)
                    )
                },
                singleLine = true,
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = Color(0xFF424242), // Grey background for TextField
                        shape = RoundedCornerShape(15.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White, // White border
                        shape = RoundedCornerShape(15.dp)
                    ),
                shape = RoundedCornerShape(15.dp),
                textStyle = TextStyle(fontSize = 14.sp, color = Color.White), // White text
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent // No underline when unfocused
                )
            )
            Column(
                modifier = Modifier
                    .height(48.dp)
                    .width(80.dp)
                    .background(
                        color = Color(0xFF424242), // Grey background
                        shape = RoundedCornerShape(15.dp)
                    )
                    .clickable {
                        focusManager.clearFocus()
                        setMessage("") // Clear text
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

@Preview(showBackground = true)
@Composable
fun SinglePageWithTextFieldPreview() {
    ElsAITheme {
        SinglePageWithTextField()
    }
}
