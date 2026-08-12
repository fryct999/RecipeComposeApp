package fixtures

import ru.fryct999.recipecomposeapp.data.model.CategoryDto

object CategoryTestFixtures {
    fun createCategoryDto(
        id: Int = 1,
        title: String = "Завтраки",
        description: String = "Утренние блюда",
        imageUrl: String = "breakfast.jpg"
    ) = CategoryDto(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
    )
}