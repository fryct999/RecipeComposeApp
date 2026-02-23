package ru.fryct999.recipecomposeapp.jcp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

class BasicElementsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        }
    }
}

@Composable
fun BasicElementsExample() {
    Card(
        modifier = Modifier
            .size(width = 400.dp, height = 600.dp)
            .background(Color.Gray)
    ) {
        var textFieldState by remember { mutableStateOf("") }

        Text(text = "Text example", Modifier.padding(top = 20.dp, start = 10.dp, bottom = 10.dp))

        TextField(
            value = textFieldState,
            onValueChange = { newText -> textFieldState = newText },
            placeholder = { Text("Enter text here.") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Image(
            painter = painterResource(id = R.drawable.img_ervar2),
            contentDescription = "description",
            modifier = Modifier
                .size(140.dp)
                .background(Color.Gray),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = R.drawable.img_ervar2),
            contentDescription = "description",
            modifier = Modifier.size(140.dp),
            contentScale = ContentScale.Crop
        )

        Button(
            onClick = {}
        ) { Text("Button") }

        OutlinedButton(
            onClick = {}
        ) { Text("OutlinedButton") }

        TextButton(
            onClick = {}
        ) { Text("TextButton") }
    }
}

@Preview
@Composable
fun BasicElementsExamplePreview() {
    RecipeComposeAppTheme {
        BasicElementsExample()
    }
}