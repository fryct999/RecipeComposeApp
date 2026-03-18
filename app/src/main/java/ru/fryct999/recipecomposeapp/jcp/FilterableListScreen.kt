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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

data class ListItem(
    val title: String,
    val category: String
)

val items = listOf(
    ListItem("Купить продукты", "Home"),
    ListItem("Сделать отчёт", "Job"),
    ListItem("Позвонить другу", "Home"),
    ListItem("Встреча с командой", "Job"),
    ListItem("Купить протекторы", "Hobby"),
)

val categoryList = listOf("Job", "Home", "Hobby")

class FilterableListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeComposeAppTheme {
                FilterableListExample()
            }
        }
    }
}

@Composable
fun FilterableListExample() {
    var selectedCategory by remember { mutableStateOf("All") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.Gray)
                .padding(start = 5.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(innerPadding),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text("Filter:")
                categoryList.forEach {
                    FilterChip(
                        onClick = {
                            selectedCategory = it
                        },
                        label = {
                            Text(it)
                        },
                        selected = selectedCategory == it,
                    )
                }
            }

            Button(
                onClick = { selectedCategory = "All" }
            ) {
                Text("Clear filters")
            }

            val filterItems = items.filter {
                it.category == selectedCategory || selectedCategory == "All"
            }

            filterItems.forEach {
                Card(
                    modifier = Modifier
                        .padding(2.dp),
                ) {
                    Text(text = it.title, modifier = Modifier.padding(2.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun FilterableListPreview() {
    RecipeComposeAppTheme {
        FilterableListExample()
    }
}