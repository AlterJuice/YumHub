package com.alterjuice.domain.model.common

enum class MealType {
    Breakfast,
    Dinner,
    Lunch,
    Supper,
    Snack,
    Dessert,
    Appetizer,
    Undefined;

    companion object {
        fun getByName(name: String): MealType {
            return values().find { it.name.equals(name, ignoreCase = true) } ?: Undefined
        }
    }
}

val foodTags = HashMap<String, List<MealCategories>>().apply {
    putAll(
        sequenceOf(

            "2" to listOf(
                MealCategories.CookTypeBaked,
                MealCategories.TypeSeafood,
                MealCategories.FlavorsSavory,
                MealCategories.AllergensShellfish
            ),
            "4" to listOf(
                MealCategories.TypeSweets,
                MealCategories.TypeDessert,
                MealCategories.TypeFruits,
                MealCategories.FlavorsSweet
            ),
            "7" to listOf(
                MealCategories.TypeSweets,
                MealCategories.TypeDessert,
                MealCategories.FlavorsSweet,
                MealCategories.AllergensNuts
            ),
            "8" to listOf(MealCategories.TypeSeafood, MealCategories.FlavorsSavory)

        )
    )
}