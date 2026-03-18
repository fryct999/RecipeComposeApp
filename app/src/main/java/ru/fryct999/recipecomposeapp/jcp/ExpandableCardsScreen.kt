package ru.fryct999.recipecomposeapp.jcp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
    val items = List(5) { "Position №${it + 1}" }
    var expandedCardIndex by remember { mutableStateOf<Int?>(null) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.Gray)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(15.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                userScrollEnabled = true,
            ) {
                items(items.size) { id ->
                    val isExpanded = expandedCardIndex == id

                    Card(
                        onClick = {
                            expandedCardIndex = if (isExpanded) null else id
                        },
                        modifier = Modifier
                            .padding(2.dp)
                            .width(180.dp),
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = items[id]
                        )

                        AnimatedVisibility(visible = isExpanded) {
                            Column(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxSize()
                            ) {
                                Text("Some extra text.")
                                Image(
                                    painter = painterResource(id = R.drawable.img_ervar2),
                                    contentDescription = "description",
                                    modifier = Modifier
                                        .size(140.dp),
                                    contentScale = ContentScale.None
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExpandableCardsPreview() {
    RecipeComposeAppTheme {
        ExpandableCardsExample()
    }
}