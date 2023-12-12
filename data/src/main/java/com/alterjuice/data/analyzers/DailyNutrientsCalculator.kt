package com.alterjuice.data.analyzers

import com.alterjuice.domain.model.user.FitnessGoal
import com.alterjuice.domain.model.user.UserInfo

object DailyNutrientsCalculator {
    private const val proteinRatio = 0.2 // from 0.15 to 0.25
    private const val fatRatio = 0.15
    private const val carbRatio = 0.25 // from 0.2 to 0.35


    fun predictDailyNutrients(
        userInfo: UserInfo,
    ): Triple<Double, Double, Double> {
        return calculateNutrientRequirements(
            tdee = TDEECalculator.calculateTDEE(userInfo),
            goal = userInfo.fitnessGoal
        )
    }

    private fun calculateNutrientRequirements(
        tdee: Double,
        goal: FitnessGoal
    ): Triple<Double, Double, Double> {

        // Adjust requirements based on fitness goal

        // 1g of protein = 4 calories
        // from 0.15 to 0.25
        val protein = when (goal) {
            FitnessGoal.LoseWeight -> tdee * 0.3 / 4
            FitnessGoal.BuildMuscle -> tdee * 0.35 / 4
            else -> tdee * proteinRatio / 4
        }

        // 1g of fat = 9 calories
        val fat = when (goal) {
            FitnessGoal.LoseWeight -> tdee * 0.25 / 9
            FitnessGoal.BuildMuscle -> tdee * 0.3 / 9
            else -> tdee * fatRatio / 9
        }

        // 1g of carbs = 4 calories
        // from 0.2 to 0.35
        val carbs = when (goal) {
            FitnessGoal.LoseWeight -> tdee * 0.45 / 4
            FitnessGoal.BuildMuscle -> tdee * 0.55 / 4
            else -> tdee * carbRatio / 4
        }

        return Triple(protein, fat, carbs)
    }

}