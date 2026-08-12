package ru.fryct999.recipecomposeapp.data.model

import org.junit.Assert.*
import org.junit.Test
import ru.fryct999.recipecomposeapp.ui.Constants

class RecipeDtoTest {
    @Test
    fun `converts DTO to UI model`() {
        val dto = RecipeDto(
            id = 0,
            title = "Классический бургер с говядиной",
            ingredients = listOf(
                IngredientDto(quantity = "0.5", unitOfMeasure = "кг", description = "говяжий фарш"),
                IngredientDto(
                    quantity = "1.0",
                    unitOfMeasure = "шт",
                    description = "луковица, мелко нарезанная"
                ),
                IngredientDto(
                    quantity = "2.0",
                    unitOfMeasure = "зубч",
                    description = "чеснок, измельченный"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "булочки для бургера"
                ),
                IngredientDto(quantity = "4.0", unitOfMeasure = "шт", description = "листа салата"),
                IngredientDto(quantity = "2.0", unitOfMeasure = "ст. л.", description = "горчица"),
                IngredientDto(quantity = "2.0", unitOfMeasure = "ст. л.", description = "кетчуп"),
                IngredientDto(
                    quantity = "по вкусу",
                    unitOfMeasure = "",
                    description = "соль и черный перец"
                ),
            ),
            method = listOf(
                "В глубокой миске смешайте говяжий фарш, лук, чеснок, соль и перец. Разделите фарш на 4 равные части и сформируйте котлеты.",
                "Разогрейте сковороду на среднем огне. Обжаривайте котлеты с каждой стороны в течение 4-5 минут или до желаемой степени прожарки.",
                "В то время как котлеты готовятся, подготовьте булочки. Разрежьте их пополам и обжарьте на сковороде до золотистой корочки.",
                "Смазать нижние половинки булочек горчицей и кетчупом, затем положите лист салата, котлету, кольца помидора и закройте верхней половинкой булочки.",
            ),
            imageUrl = "burger-hamburger.png"
        )
        val result = dto.toUiModel()

        assertEquals(0, result.id)
        assertEquals("Классический бургер с говядиной", result.title)
        assertEquals(
            listOf(
                IngredientDto(quantity = "0.5", unitOfMeasure = "кг", description = "говяжий фарш"),
                IngredientDto(
                    quantity = "1.0",
                    unitOfMeasure = "шт",
                    description = "луковица, мелко нарезанная"
                ),
                IngredientDto(
                    quantity = "2.0",
                    unitOfMeasure = "зубч",
                    description = "чеснок, измельченный"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "булочки для бургера"
                ),
                IngredientDto(quantity = "4.0", unitOfMeasure = "шт", description = "листа салата"),
                IngredientDto(quantity = "2.0", unitOfMeasure = "ст. л.", description = "горчица"),
                IngredientDto(quantity = "2.0", unitOfMeasure = "ст. л.", description = "кетчуп"),
                IngredientDto(
                    quantity = "по вкусу",
                    unitOfMeasure = "",
                    description = "соль и черный перец"
                ),
            ).map { it.toUiModel() },
            result.ingredients
        )
        assertEquals(
            listOf(
                "В глубокой миске смешайте говяжий фарш, лук, чеснок, соль и перец. Разделите фарш на 4 равные части и сформируйте котлеты.",
                "Разогрейте сковороду на среднем огне. Обжаривайте котлеты с каждой стороны в течение 4-5 минут или до желаемой степени прожарки.",
                "В то время как котлеты готовятся, подготовьте булочки. Разрежьте их пополам и обжарьте на сковороде до золотистой корочки.",
                "Смазать нижние половинки булочек горчицей и кетчупом, затем положите лист салата, котлету, кольца помидора и закройте верхней половинкой булочки.",
            ),
            result.method
        )
        assertEquals(Constants.IMAGES_BASE_URL + dto.imageUrl, result.imageUrl)
    }
}