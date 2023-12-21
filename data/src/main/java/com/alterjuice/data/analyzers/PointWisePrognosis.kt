package com.alterjuice.data.analyzers

import com.alterjuice.domain.model.common.MealCategories
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutritionEnum
import com.alterjuice.domain.model.nutrition.valueOrZero
import com.alterjuice.domain.model.user.FitnessGoal
import com.alterjuice.domain.model.user.UserInfo
import com.alterjuice.domain.model.user.UserPAL

object PointWisePrognosis {

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

    // Додатковий коефіцієнт нутрієнтів (протеїн) в залежності від цілей користувача
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

    private val proteinPalWeight = mapOf(
        UserPAL.Sedentary to 0.8,
        UserPAL.LightlyActive to 1.0,
        UserPAL.ModeratelyActive to 1.2,
        UserPAL.VeryActive to 1.5,
        UserPAL.ExtraActive to 1.5
    )

    private val carbsPalWeight = mapOf(
        UserPAL.Sedentary to 0.9,
        UserPAL.LightlyActive to 1.1,
        UserPAL.ModeratelyActive to 1.3,
        UserPAL.VeryActive to 1.5,
        UserPAL.ExtraActive to 1.5
    )

    private val fatPalWeight = mapOf(
        UserPAL.Sedentary to 0.8,
        UserPAL.LightlyActive to 1.0,
        UserPAL.ModeratelyActive to 1.2,
        UserPAL.VeryActive to 1.5,
        UserPAL.ExtraActive to 1.5
    )

    private val caloriesPalWeight = mapOf(
        UserPAL.Sedentary to 0.9,
        UserPAL.LightlyActive to 1.1,
        UserPAL.ModeratelyActive to 1.3,
        UserPAL.VeryActive to 1.5,
        UserPAL.ExtraActive to 1.5
    )


    private val selectionWeight = 1.0
    private val goalWeight = 0.5
    private val palWeight = 0.5


    fun calculateWeightsForProductsNormalized(
        products: List<YumHubMeal>,
        userInfo: UserInfo,
        eatenCategories: Map<MealCategories, Int>,
    ): Map<YumHubMeal, Double/*from 0 to 1*/> {
        val allWeights = calculateWeightsForProducts(
            products = products,
            userInfo = userInfo,
            eatenCategories = eatenCategories
        )
        val maxNonNormalizedWeight = allWeights.maxBy { it.second }.second
        return allWeights.map {
            it.copy(second = it.second.div(maxNonNormalizedWeight))
        }.toMap()
    }


    private val favoriteWeight = 5.0
    private val mainDishCategoryWeight = 3.5
    private val eatenWeightCoefficient = 0.2

    fun calculateWeightsForProducts(
        products: List<YumHubMeal>,
        userInfo: UserInfo,
        eatenCategories: Map<MealCategories, Int>,
    ): List<Pair<YumHubMeal, Double>> {
        return products.map { product ->
            val favoriteWeightToAdd = product.categoriesTags.count {
                userInfo.favoriteCategories.contains(it)
            } * favoriteWeight
            val exactTypesWeights = product.categoriesTags.filter { it.isSpecifiedType() }.count {
                userInfo.favoriteCategories.contains(it)
            } * mainDishCategoryWeight
            val eatenWeightToAdd = product.categoriesTags.sumOf {
                calculateEatenWeight(eatenCategories, it)
            }
            val goalScore = calculateGoalScore(product, userInfo.fitnessGoal)
            val palScore = calculatePalScore(product, userInfo.pal)

            // Загальна вага продукту
            val totalScore = (goalScore * goalWeight) + (palScore * palWeight) +
                    favoriteWeightToAdd + exactTypesWeights + eatenWeightToAdd

            product to totalScore
        }
    }

    // Calculate eaten weight with using a coefficient
    private fun calculateEatenWeight(
        eatenCategories: Map<MealCategories, Int>,
        category: MealCategories
    ): Double {
        val timesEaten = eatenCategories.getOrElse(category) { 0 }
        return timesEaten * eatenWeightCoefficient
    }

    private fun calculateGoalScore(product: YumHubMeal, userGoal: FitnessGoal): Double {
        val fatGoalWeight = fatWeight[userGoal] ?: 1.0
        val caloriesGoalWeight = caloriesWeight[userGoal] ?: 1.0
        val proteinGoalWeight = proteinWeight[userGoal] ?: 1.0
        val carbsGoalWeight = carbsWeight[userGoal] ?: 1.0

        return (product.withOneServing(NutritionEnum.Fat) * fatGoalWeight) +
                (product.calories * caloriesGoalWeight) +
                (product.withOneServing(NutritionEnum.Protein) * proteinGoalWeight) +
                (product.withOneServing(NutritionEnum.Carbs) * carbsGoalWeight)
    }


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