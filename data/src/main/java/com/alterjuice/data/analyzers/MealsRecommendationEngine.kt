package com.alterjuice.data.analyzers

import com.alterjuice.data.data.getMealWithRecipeItemsAsYumHubMeals
import com.alterjuice.domain.model.common.MealCategories
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutritionEnum
import com.alterjuice.domain.model.user.FitnessGoal
import com.alterjuice.domain.model.user.UserInfo

enum class DayPart {
    Morning, Afternoon, Evening;
}

class MealRecommendationEngine {
    private val favoriteWeight = 5.0
    private val mainDishCategoryWeight = 3.5
    private val eatenWeightCoefficient = 0.2

    // Additional coefficients for carbs, fats and proteins depending on day time (DayPart)
    private val carbDayPartCoefficients = mapOf(
        DayPart.Morning to 1.0,
        DayPart.Afternoon to 1.2,
        DayPart.Evening to 0.8
    )

    private val proteinDayPartCoefficients = mapOf(
        DayPart.Morning to 1.2,
        DayPart.Afternoon to 1.0,
        DayPart.Evening to 0.9
    )

    private val fatDayPartCoefficients = mapOf(
        DayPart.Morning to 1.0,
        DayPart.Afternoon to 0.9,
        DayPart.Evening to 1.2
    )

    // Additional coefficients for carbs, fats and proteins depending onuser fitness goals

    private val proteinGoalCoefficients = mapOf(
        FitnessGoal.LoseWeight to 2.0,
        FitnessGoal.MaintainWeight to 1.5,
        FitnessGoal.BuildMuscle to 3.0,
    )

    private val fatGoalCoefficients = mapOf(
        FitnessGoal.LoseWeight to 0.25, // Lower fat intake for weight loss
        FitnessGoal.MaintainWeight to 0.3, // Moderate fat intake for weight maintenance
        FitnessGoal.BuildMuscle to 0.35, // Higher fat intake for muscle building
    )

    private val carbGoalCoefficients = mapOf(
        FitnessGoal.LoseWeight to 2.0, // Moderate carbohydrate intake for weight loss
        FitnessGoal.MaintainWeight to 2.5, // Balanced carbohydrate intake for weight maintenance
        FitnessGoal.BuildMuscle to 3.0, // Higher carbohydrate intake for muscle building
    )

    fun recommendMeals(
        userInfo: UserInfo,
        dayPart: DayPart,
        eatenCategories: Map<MealCategories, Int>,
        meals: List<YumHubMeal>
    ): List<Pair<YumHubMeal, Double>> {
        // Calculate weights for each MealCategory
        val mealToWeight = meals.associateWith { meal ->

            // Add a favouriteWeight for each favourite tag presents
            val favoriteWeightToAdd = meal.categoriesTags.count {
                userInfo.favoriteCategories.contains(it)
            } * favoriteWeight

            // Count the Exact type the meal have and if yes - check if user marked it as favourite
            // if yes - add some weights
            val exactTypesWeights = meal.categoriesTags.filter { it.isSpecifiedType() }.count {
                userInfo.favoriteCategories.contains(it)
            } * mainDishCategoryWeight

            // Add some of weights if user already ate this type of food by category
            val eatenWeightToAdd = meal.categoriesTags.sumOf {
                calculateEatenWeight(eatenCategories, it)
            }

            val nutrientScore = calculateNutrientScore(userInfo.fitnessGoal, dayPart, meal)
            val weight = favoriteWeightToAdd + exactTypesWeights + eatenWeightToAdd + nutrientScore
            weight
        }.filter { !it.value.isNaN() }

        // Sort MealCategories based on weights
        val sortedMeals = mealToWeight.entries.sortedByDescending { it.value }

        // Extract the sorted MealCategories with weights
        return sortedMeals.map { it.key to it.value }
    }

    private fun isMainDishCategory(category: MealCategories): Boolean {
        return category.name.startsWith("Type")
    }

    // Calculate eaten weight with using a coefficient
    private fun calculateEatenWeight(
        eatenCategories: Map<MealCategories, Int>,
        category: MealCategories
    ): Double {
        val timesEaten = eatenCategories.getOrElse(category) { 0 }
        return timesEaten * eatenWeightCoefficient
    }

    private fun calculateNutrientScore(
        fitnessGoal: FitnessGoal,
        dayPart: DayPart,
        meal: YumHubMeal
    ): Double {
        // Example: Assume you have nutrient information for each meal
        val proteinContent = meal.withServings(1.0, NutritionEnum.Protein)
        val carbContent = meal.withServings(1.0, NutritionEnum.Carbs)
        val fatContent = meal.withServings(1.0, NutritionEnum.Fat)

        val weightByGoal = calculateNutrientsScoreByGoal(
            proteinContent = proteinContent,
            carbContent = carbContent,
            fatContent = fatContent,
            fitnessGoal = fitnessGoal
        )
        val weightByDayPart = calculateNutrientsScoreDayByPart(
            proteinContent = proteinContent,
            carbContent = carbContent,
            fatContent = fatContent,
            dayPart = dayPart
        )
        // Calculate nutrient score based on nutrient content and weights
        return weightByGoal + weightByDayPart
    }

    private fun calculateNutrientsScoreByGoal(
        proteinContent: Double, carbContent: Double, fatContent: Double,
        fitnessGoal: FitnessGoal
    ): Double {
        val proteinGoalCoefficient = proteinGoalCoefficients[fitnessGoal]?: 1.0
        val carbGoalCoefficient = carbGoalCoefficients[fitnessGoal]?: 1.0
        val fatGoalCoefficient = fatGoalCoefficients[fitnessGoal]?: 1.0

        return ((proteinContent * proteinGoalCoefficient)
                + (carbContent * carbGoalCoefficient)
                + (fatContent * fatGoalCoefficient))
    }

    private fun calculateNutrientsScoreDayByPart(
        proteinContent: Double, carbContent: Double, fatContent: Double,
        dayPart: DayPart
    ): Double {
        val proteinGoalCoefficient = proteinDayPartCoefficients[dayPart]?: 1.0
        val carbDayPartCoefficient = carbDayPartCoefficients[dayPart]?: 1.0
        val fatDayPartCoefficient = fatDayPartCoefficients[dayPart]?: 1.0
        return ((proteinContent * proteinGoalCoefficient)
                + (carbContent * carbDayPartCoefficient)
                + (fatContent * fatDayPartCoefficient))
    }
}

fun main() {
    // Example: Simulating user preferences
    val meals = getMealWithRecipeItemsAsYumHubMeals()
    val eaten = List(5) { meals.random() }
    //val userPreferences = UserPreferences(
    //    favoriteCategories = setOf(
    //        MealCategories.CuisineItalian,
    //        MealCategories.DietaryPreferencesVegetarian,
    //        MealCategories.FlavorsSweet
    //    ),
    //    eatenMeals = eaten,
    //    fitnessGoal = FitnessGoal.BuildMuscle
    //)
    //// Creating the recommendation engine
    //val mealRecommendationEngine = MealRecommendationEngine(
    //    userPreferences,
    //    meals = meals,
    //)

    // Getting meal recommendations with weights
    // val recommendedMeals = mealRecommendationEngine.recommendMeals()

    // Displaying the recommendations with weights
    // recommendedMeals.forEach { (mealCategory, weight) ->
    //     println("Category: $mealCategory, Weight: $weight")
    // }
}