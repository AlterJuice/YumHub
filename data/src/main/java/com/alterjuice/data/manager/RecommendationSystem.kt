package com.alterjuice.data.manager

import com.alterjuice.domain.model.common.YumHubMeal


data class FoodRecommendation(
    val food: List<YumHubMeal>,
    val description: String
)

class RecommendationsManager {

    fun getCalciumRichFoodForBoneHealthRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val calciumThreshold = 200 // Replace with your threshold for calcium content
        val calciumRichFoods = myFoodList.filter {
            val nutrientValue = it.calcium?.value?: return@filter false
            nutrientValue > calciumThreshold
        }

        return FoodRecommendation(calciumRichFoods,"Rich in calcium for bone health.")
    }

    fun getFiberRichFoodForDigestiveHealthRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val fiberThreshold = 5.0 // Replace with your threshold for fiber content
        val fiberRichFoods = myFoodList.filter {
            val nutrientValue = it.fiber?.value?: return@filter false
            nutrientValue > fiberThreshold
        }

        return FoodRecommendation(fiberRichFoods, "High in fiber for digestive health.")
    }

    fun getPotassiumRichFoodForHeartHealthRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val potassiumThreshold = 300 // Replace with your threshold for potassium content
        val potassiumRichFoods = myFoodList.filter {
            val nutrientValue = it.potassium?.value?: return@filter false
            nutrientValue > potassiumThreshold
        }

        return FoodRecommendation(potassiumRichFoods, "Excellent source of potassium for heart health.")
    }

    fun getProteinRichFoodForMuscleSupportRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val proteinThreshold = 10.0 // Replace with your threshold for protein content
        val proteinRichFoods = myFoodList.filter {
            val nutrientValue = it.protein?.value?: return@filter false
            nutrientValue > proteinThreshold
        }

        return FoodRecommendation(proteinRichFoods,"High in protein for muscle support.")
    }

    fun getIronRichFoodForBloodHealthRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val ironThreshold = 3.0 // Replace with your threshold for iron content
        val ironRichFoods = myFoodList.filter {
            val nutrientValue = it.iron?.value?: return@filter false
            nutrientValue > ironThreshold
        }

        return FoodRecommendation(ironRichFoods, "Excellent source of iron for blood health.")
    }

    fun getVitaminDRichFoodForBoneHealthRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val vitaminDThreshold = 5.0 // Replace with your threshold for vitamin D content
        val vitaminDRichFoods = myFoodList.filter {
            val nutrientValue = it.vitaminD?.value?: return@filter false
            nutrientValue > vitaminDThreshold
        }

        return FoodRecommendation(vitaminDRichFoods, "Rich in vitamin D for bone health.")
    }

    fun getLowSodiumFoodForHeartHealthRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val sodiumThreshold = 100 // Replace with your threshold for sodium content
        val lowSodiumFoods = myFoodList.filter {
            val nutrientValue = it.sodiumNa?.value?: return@filter false
            nutrientValue < sodiumThreshold
        }

        return FoodRecommendation(lowSodiumFoods,"Low in sodium for heart health.")
    }

    fun getLowSugarFoodForDentalHealthRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val sugarThreshold = 5.0 // Replace with your threshold for sugar content
        val lowSugarFoods = myFoodList.filter {
            val nutrientValue = it.sugars?.value?: return@filter false
            nutrientValue < sugarThreshold
        }

        return FoodRecommendation(lowSugarFoods,"Low in sugar for dental health.")
    }

    fun getProteinForMuscleGrowthRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val proteinThreshold = 15.0 // Replace with your threshold for protein content
        val highProteinFoods = myFoodList.filter {
            val nutrientValue = it.protein?.value?: return@filter false
            nutrientValue > proteinThreshold
        }

        return FoodRecommendation(highProteinFoods, "Rich in protein for muscle growth.")
    }


    fun getHealthyFatsForEnergyAndFatBurningRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val healthyFatThreshold = 5.0 // Replace with your threshold for healthy fat content
        val healthyFatFoods = myFoodList.filter {
            val nutrientValue = it.fat?.value?: return@filter false
            nutrientValue > healthyFatThreshold
        }

        return FoodRecommendation(healthyFatFoods, "Contains healthy fats for energy and fat burning.")
    }


    fun getLowCarbForFatBurningRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val carbThreshold = 10.0 // Replace with your threshold for carbohydrate content
        val lowCarbFoods = myFoodList.filter {
            val nutrientValue = it.carbs?.value?: return@filter false
            nutrientValue < carbThreshold
        }

        return FoodRecommendation(lowCarbFoods, "Low in carbs for fat burning.")
    }

    fun getCaffeineForEnergyBoostRecommendations(myFoodList: List<YumHubMeal>): FoodRecommendation {
        val caffeineThreshold = 30 // Replace with your threshold for caffeine content
        val caffeineFoods = myFoodList.filter {
            val nutrientValue = it.caffeine?.value?: return@filter false
            nutrientValue > caffeineThreshold
        }

        return FoodRecommendation(caffeineFoods,"Contains caffeine for an energy boost.")
    }
}