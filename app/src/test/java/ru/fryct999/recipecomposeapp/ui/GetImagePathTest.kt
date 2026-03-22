package ru.fryct999.recipecomposeapp.ui

import org.junit.Assert.*
import org.junit.Test

class GetImagePathTest {
    @Test
    fun checkHTTP() {
        val url = "https://www.twitch.tv/forsen"
        assertEquals(url, getImagePath(url))
    }

    @Test
    fun checkFile() {
        val input = "burger.png"
        val result = Constants.ASSETS_URI_PREFIX + input
        assertEquals(result, getImagePath(input))
    }
}