package ru.fryct999.recipecomposeapp.ui

import org.junit.Assert.*
import org.junit.Test
import ru.fryct999.recipecomposeapp.core.utils.getImagePath

class GetImagePathTest {
    @Test
    fun checkHTTP() {
        val url = "https://www.twitch.tv/forsen"
        assertEquals(url, getImagePath(url))
    }

    @Test
    fun checkFile() {
        val input = "burger.png"
        val result = Constants.IMAGES_BASE_URL + input
        assertEquals(result, getImagePath(input))
    }
}