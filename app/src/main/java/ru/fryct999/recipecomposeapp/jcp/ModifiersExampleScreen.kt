package ru.fryct999.recipecomposeapp.jcp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

class ModifiersExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        }
    }
}

@Composable
fun ModifiersExample(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, textAlign = TextAlign.Center)
    Box(modifier = modifier)
}

@Preview
@Composable
fun ClipPreview() {
    RecipeComposeAppTheme {
        ModifiersExample(
            text = "Clip",
            modifier = Modifier
                .padding(24.dp)
                .size(100.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.Red),
        )
    }
}

@Preview
@Composable
fun BorderPreview() {
    RecipeComposeAppTheme {
        ModifiersExample(
            text = "Border",
            modifier = Modifier
                .padding(24.dp)
                .size(100.dp)
                .border(width = 4.dp, color = Color.Blue)
                .background(Color.Red)
        )
    }
}

@Preview
@Composable
fun ShapePreview() {
    RecipeComposeAppTheme {
        Column(
            modifier = Modifier
                .size(250.dp)
                .background(Color.Gray)
        ) {
            var clickCount by remember { mutableIntStateOf(0) }

            Text(
                text = "Inside padding. Clicks: $clickCount",
                Modifier
                    .padding(15.dp)
                    .background(Color.Red)
                    .clickable { clickCount += 1 }
            )

            ModifiersExample(
                text = "Shape & outside padding",
                modifier = Modifier
                    .padding(25.dp)
                    .size(100.dp)
                    .background(Color.Green, shape = CutCornerShape(20.dp))

            )
        }
    }
}