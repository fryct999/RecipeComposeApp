package ru.fryct999.recipecomposeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.fryct999.recipecomposeapp.data.database.dao.CategoryDao
import ru.fryct999.recipecomposeapp.data.database.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        fun buildDatabase(context: Context) : RecipesDatabase {
            return Room.databaseBuilder(context, RecipesDatabase::class.java, "recipes_database").fallbackToDestructiveMigration().build()
        }
    }
}

