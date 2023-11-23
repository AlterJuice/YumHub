package com.alterjuice.domain.model.common

import com.alterjuice.domain.model.nutrition.NutritionEnum
import com.alterjuice.domain.model.nutrition.by


enum class PredefinedYumHubMealFilters(): YumHubMealFilters {
    LESS_COOK_TIME {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return (dish.cookInfo?.cookTime?: return false) < 200
        }
    },
    LESS_INGREDIENTS {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return (dish.cookInfo?.ingredients?.size?: return false) < 3
        }
    },
    LOW_PRICED {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return (dish.cookInfo?.ingredients?.size?: return false) < 3
        }
    },
    HIGH_PRICED {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return (dish.cookInfo?.ingredients?.size?: return false) > 4
        }
    },
    LOW_CALORIE {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return dish.calories < 50
        }
    },
    HIGH_CALORIE {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return dish.calories > 200
        }
    },
    LOW_FAT {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return (dish.nutrients.by(NutritionEnum.Fat)?.value?: return false) < 5
        }
    },
    FAST_FOOD {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return dish.categoriesTags.contains(MealCategories.TypeFastFood)
        }
    },
    NO_FILTERS {
        override fun check(dish: YumHubMeal): Boolean {
            // TODO CHECK Condition
            return true
        }
    };
}
