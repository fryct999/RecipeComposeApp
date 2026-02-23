package ru.fryct999.recipecomposeapp.jcp

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

class DeclarativeComponentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Composable
fun DeclarativeExample(name: String, modifier: Modifier = Modifier) {
    val isModerator = name == "Moderator"
    val textColor = if(isModerator) Color.Red else Color.Black

    Text(
        text = "Hello $name!",
        modifier = modifier,
        fontSize = 32.sp,
        color = textColor,
    )
}

@Preview(showBackground = true)
@Composable
fun DeclarativeExamplePreviewModerator() {
    RecipeComposeAppTheme {
        DeclarativeExample("Moderator", Modifier.background(Color.Blue))
    }
}

@Preview(showBackground = true)
@Composable
fun DeclarativeExamplePreviewUser() {
    RecipeComposeAppTheme {
        DeclarativeExample("Fryct")
    }
}