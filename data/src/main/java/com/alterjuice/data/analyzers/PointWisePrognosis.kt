package com.alterjuice.data.analyzers

import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutritionEnum
import com.alterjuice.domain.model.nutrition.valueOrZero
import com.alterjuice.domain.model.user.FitnessGoal
import com.alterjuice.domain.model.user.UserPAL

class PointWisePrognosis {

    // Веса для нутриентов в зависимости от цели пользователя
    private val fatWeight = mapOf(
        FitnessGoal.LoseWeight to 0.7,
        FitnessGoal.BuildMuscle to 1.2,
        FitnessGoal.MaintainWeight to 1.0
    )

    private val caloriesWeight = mapOf(
        FitnessGoal.LoseWeight to 0.9,
        FitnessGoal.BuildMuscle to 1.1,
        FitnessGoal.MaintainWeight to 1.0
    )

    // Веса для нутриентов в зависимости от цели пользователя
    private val proteinWeight = mapOf(
        FitnessGoal.LoseWeight to 1.0,
        FitnessGoal.BuildMuscle to 1.2,
        FitnessGoal.MaintainWeight to 1.0
    )
    private val carbsWeight = mapOf(
        FitnessGoal.LoseWeight to 0.8,
        FitnessGoal.BuildMuscle to 1.0,
        FitnessGoal.MaintainWeight to 1.0
    )

    // Веса для протеинов в зависимости от уровня PAL
    private val proteinPalWeight = mapOf(
        UserPAL.Sedentary to 0.8,
        UserPAL.LightlyActive to 1.0,
        UserPAL.ModeratelyActive to 1.2,
        UserPAL.VeryActive to 1.5,
        UserPAL.ExtraActive to 1.5
    )

    // Веса для углеводов в зависимости от уровня PAL
    private val carbsPalWeight = mapOf(
        UserPAL.Sedentary to 0.9,
        UserPAL.LightlyActive to 1.1,
        UserPAL.ModeratelyActive to 1.3,
        UserPAL.VeryActive to 1.5,
        UserPAL.ExtraActive to 1.5
    )

    // Веса для жира в зависимости от уровня PAL
    private val fatPalWeight = mapOf(
        UserPAL.Sedentary to 0.8,
        UserPAL.LightlyActive to 1.0,
        UserPAL.ModeratelyActive to 1.2,
        UserPAL.VeryActive to 1.5,
        UserPAL.ExtraActive to 1.5
    )

    // Веса для калорий в зависимости от уровня PAL
    private val caloriesPalWeight = mapOf(
        UserPAL.Sedentary to 0.9,
        UserPAL.LightlyActive to 1.1,
        UserPAL.ModeratelyActive to 1.3,
        UserPAL.VeryActive to 1.5,
        UserPAL.ExtraActive to 1.5
    )


    // Веса для различных параметров
    private val selectionWeight = 1.0 // Вес для количества раз, которое продукт был выбран
    private val goalWeight = 0.5
    private val palWeight = 0.5


    fun calculateWeightsForProductsNormalized(
        products: List<YumHubMeal>,
        userGoal: FitnessGoal,
        userPal: UserPAL
    ): Map<YumHubMeal, Double/*from 0 to 1*/> {
        val allWeights = calculateWeightsForProducts(products, userGoal, userPal)
        val maxNonNormalizedWeight = allWeights.maxBy { it.second }.second
        return allWeights.map {
            it.copy(second = it.second.div(maxNonNormalizedWeight))
        }.toMap()
    }

    // Метод для расчета матрицы предпочтений пользователя
    fun calculateWeightsForProducts(
        products: List<YumHubMeal>,
        userGoal: FitnessGoal,
        userPal: UserPAL
    ): List<Pair<YumHubMeal, Double>> {
        return products.map { product ->
            val selectionScore = 0.0//product.selectionCount * selectionWeight
            val goalScore = calculateGoalScore(product, userGoal)
            val palScore = calculatePalScore(product, userPal)

            // Общий вес для продукта
            val totalScore = selectionScore + (goalScore * goalWeight) + (palScore * palWeight)

            product to totalScore
        }
    }

    private fun calculateGoalScore(product: YumHubMeal, userGoal: FitnessGoal): Double {
        val fatScore = product.nutrients.find { it.attr == NutritionEnum.Fat }?.value ?: 0.0
        val caloriesScore = product.calories

        val fatGoalWeight = fatWeight[userGoal] ?: 1.0
        val caloriesGoalWeight = caloriesWeight[userGoal] ?: 1.0
        val proteinGoalWeight = proteinWeight[userGoal] ?: 1.0
        val carbsGoalWeight = carbsWeight[userGoal] ?: 1.0

        return (fatScore * fatGoalWeight) +
                (caloriesScore * caloriesGoalWeight) +
                (product.protein.valueOrZero * proteinGoalWeight) +
                (product.carbs.valueOrZero * carbsGoalWeight)
    }

    // Метод для расчета веса в зависимости от уровня PAL
    private fun calculatePalScore(product: YumHubMeal, userPal: UserPAL): Double {
        val fatScore = product.nutrients.find { it.attr == NutritionEnum.Fat }?.value ?: 0.0
        val caloriesScore = product.calories

        val fatPalWeight = fatPalWeight[userPal] ?: 1.0
        val caloriesPalWeight = caloriesPalWeight[userPal] ?: 1.0
        val proteinPalWeight = proteinPalWeight[userPal] ?: 1.0
        val carbsPalWeight = carbsPalWeight[userPal] ?: 1.0

        return (fatScore * fatPalWeight) +
                (caloriesScore * caloriesPalWeight) +
                (product.protein.valueOrZero * proteinPalWeight) +
                (product.carbs.valueOrZero * carbsPalWeight)
    }
}