package ru.fryct999.recipecomposeapp.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType

class DecodedStringNavType : NavType<String>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): String? = bundle.getString(key)

    override fun parseValue(value: String): String = Uri.decode(value)

    override fun put(bundle: Bundle, key: String, value: String) {
        bundle.putString(key, value)
    }
}