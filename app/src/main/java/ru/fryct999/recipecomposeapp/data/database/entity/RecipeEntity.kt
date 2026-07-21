package ru.fryct999.recipecomposeapp.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipes",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val categoryId: Int,
    val imageUrl: String,
    val ingredients: String,
    val method: String,
)