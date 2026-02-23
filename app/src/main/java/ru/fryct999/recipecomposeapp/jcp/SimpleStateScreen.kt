package ru.fryct999.recipecomposeapp.jcp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class SimpleStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleStateExample()
        }
    }
}

@Composable
fun SimpleStateExample() {
    var checked by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    val bgColor = if (checked) Color.Gray else Color.White
    val textColor = if (checked) Color.DarkGray else Color.Black
    val textFieldColor = if (checked) Color.LightGray else Color.DarkGray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(40.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                }
            )
            Text(text = "Dark theme", color = textColor)
        }

        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            placeholder = { Text("Enter text here.") },
            colors = TextFieldDefaults.colors(
                focusedTextColor = textFieldColor,
                unfocusedContainerColor = textFieldColor,
            ),
            textStyle = TextStyle(color = textColor, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
    }
}

@Preview
@Composable
fun SimpleStateExamplePreview() {
    SimpleStateExample()
}