package ru.fryct999.recipecomposeapp.jcp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

class ExpandableCardsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeComposeAppTheme {
                ExpandableCardsExample()
            }
        }
    }
}

@Composable
fun ExpandableCardsExample() {

}

@Preview
@Composable
fun ExpandableCardsPreview() {
    RecipeComposeAppTheme {
        ExpandableCardsExample()
    }
}