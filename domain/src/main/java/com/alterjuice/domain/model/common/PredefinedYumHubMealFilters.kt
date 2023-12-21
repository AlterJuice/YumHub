package com.alterjuice.domain.model.common


enum class PredefinedYumHubMealFilters(
    val description: String
): YumHubMealFilters {
    LESS_COOK_TIME("Less cook time") {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return (dish.cookInfo?.cookTime?: return false) < 200
        }
    },
    LESS_INGREDIENTS("Less ingredients") {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return (dish.cookInfo?.ingredients?.size?: return false) < 3
        }
    },
    LOW_PRICED("Low priced") {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return (dish.cookInfo?.ingredients?.size?: return false) < 3
        }
    },
    HIGH_PRICED("High priced") {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return (dish.cookInfo?.ingredients?.size?: return false) > 4
        }
    },
    LOW_CALORIE("Low calorie") {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return dish.calories < 50
        }
    },
    HIGH_CALORIE("High calorie") {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return dish.calories > 200
        }
    },
    LOW_FAT("Low fat") {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return (dish.fat?.value?: return false) < 5
        }
    },
    FAST_FOOD("Fast food") {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return dish.categoriesTags.contains(MealCategories.TypeFastFood)
        }
    },
    NO_FILTERS("No filters") {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return true
        }
    };
}
