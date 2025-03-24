package com.ryancode.jokeapp.mvvm.data.model.joke

data class JokeCategoryResponse(
    val error: Boolean,
    val categories: List<String>,
    val categoryAliases: List<CategoryAlias>,
    val timestamp: Long
)

data class CategoryAlias(
    val alias: String,
    val resolved: String
)

data class CategoryAndAlias(
    val nameCategory: String,
    val alias: String?
)

fun mapCategoriesWithAliases(response: JokeCategoryResponse): List<CategoryAndAlias> {
    return response.categories.map { category ->
        val alias = response.categoryAliases.find { it.resolved == category }?.alias
        CategoryAndAlias(nameCategory = category, alias = alias)
    }
}