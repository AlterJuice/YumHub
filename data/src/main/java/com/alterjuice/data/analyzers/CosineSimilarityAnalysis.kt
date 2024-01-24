package com.alterjuice.data.analyzers

import com.alterjuice.data.data.getMealWithRecipeItemsAsYumHubMeals
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutrientsItem
import com.alterjuice.domain.model.nutrition.NutritionEnum
import com.alterjuice.utils.extensions.contains
import java.math.RoundingMode
import kotlin.math.sqrt

fun check() {

    fun Double.roundTo(digits: Int): Double {
        return toBigDecimal().setScale(digits, RoundingMode.HALF_EVEN).toDouble()
    }

    fun Float.roundTo(digits: Int): Float {
        return toBigDecimal().setScale(digits, RoundingMode.HALF_EVEN).toFloat()
    }

    fun Float.gracefulRound(digits: Int = 1): Number {
        if (this == toBigDecimal().setScale(0, RoundingMode.HALF_EVEN).toFloat()) {
            return this.toLong()
        } else {
            return roundTo(digits)
        }
    }

    fun Double.gracefulRound(digits: Int = 1): Number {
        if (this == toBigDecimal().setScale(0, RoundingMode.HALF_EVEN).toDouble()) {
            return this.toLong()
        } else {
            return roundTo(digits)
        }
    }
    AbsoluteDifferenceSimilarityAnlysis.createSimilarityMatrixBetweenMeals(getMealWithRecipeItemsAsYumHubMeals()).take(10).map { it.take(10).toDoubleArray().joinToString("|") { it.gracefulRound(5).toString() } }.joinToString("\n")

    getMealWithRecipeItemsAsYumHubMeals().let {
        listOf(it.get(1), it.get(9))
    }.map { pr ->
        AbsoluteDifferenceSimilarityAnlysis.mainNutrientsToCompare.map {
            it to pr.withOneServing(it)
        }
    }
}


object CosineSimilarityAnalysis {
    private val nutrientWeight = 0.3
    private val categoryWeight = 0.5

    val mainNutrientsToCompare = listOf(
        NutritionEnum.Protein,
        NutritionEnum.Carbs,
        NutritionEnum.Fat,
        NutritionEnum.FatSat,
        NutritionEnum.Sugars,
        NutritionEnum.EnergyCalories
    )

    // Entry point for one meal
    fun calculateCosineSimilarityForMeal(
        productToAnalyze: YumHubMeal,
        products: List<YumHubMeal>,
    ): List<Pair<YumHubMeal, Double>> {
        return products.map { product ->
            product to calculateOverallSimilarity(
                product1 = productToAnalyze,
                product2 = product,
                nutrientWeight = nutrientWeight,
                categoryWeight = categoryWeight
            )
        }
    }


    // Entry point for all meals
    fun createSimilarityMatrixBetweenMeals(products: List<YumHubMeal>): Array<DoubleArray> {
        val matrixSize = products.size

        val similarityMatrix = Array(matrixSize) { i ->
            DoubleArray(matrixSize) { j ->
                if (i == j) {
                    1.0 // Similarity between the same item is always 1
                } else {
                    calculateOverallSimilarity(
                        product1 = products[i],
                        product2 = products[j]
                    ) // Calculating similarity between two items
                }
            }
        }

        return similarityMatrix
    }

    // Entry point for between two meals
    fun calculateOverallSimilarity(
        product1: YumHubMeal,
        product2: YumHubMeal
    ): Double {
        return calculateOverallSimilarity(
            product1 = product1,
            product2 = product2,
            nutrientWeight = nutrientWeight,
            categoryWeight = categoryWeight
        )
    }

    // Entry point for between two meals
    fun calculateOverallSimilarity(
        product1: YumHubMeal,
        product2: YumHubMeal,
        nutrientWeight: Double,
        categoryWeight: Double
    ): Double {
        val nutrientSimilarity = calculateCosineSimilarity(product1, product2)
        val categorySimilarity = calculateCategorySimilarity(product1, product2)

        return (nutrientWeight * nutrientSimilarity + categoryWeight * categorySimilarity) / (nutrientWeight + categoryWeight)
    }


    private fun calculateCosineSimilarity(
        product1: YumHubMeal,
        product2: YumHubMeal
    ): Double {
        val nutrients1 = product1.nutrients.filter { attr ->
            mainNutrientsToCompare.contains {
                it.attrID == attr.attr.attrID
            }
        }

        val nutrients2 = product2.nutrients.filter { attr ->
            mainNutrientsToCompare.contains {
                it.attrID == attr.attr.attrID
            }
        }

        val dotProduct = nutrients1.mapNotNull { nutrient1 ->
            nutrients2.firstOrNull { it.attr.attrID == nutrient1.attr.attrID }?.let { nutrient2 ->
                product1.withOneServing(nutrient1.attr) * product2.withOneServing(nutrient2.attr)
            }
        }.sum()

        val magnitude1 = calculateMagnitude(product1, nutrients1)
        val magnitude2 = calculateMagnitude(product2, nutrients2)

        return if (magnitude1 > 0 && magnitude2 > 0) {
            dotProduct / (magnitude1 * magnitude2)
        } else {
            0.0
        }
    }

    private fun calculateCategorySimilarity(product1: YumHubMeal, product2: YumHubMeal): Double {
        val categories1 = product1.categoriesTags.toSet()
        val categories2 = product2.categoriesTags.toSet()

        val intersectionSize = categories1.intersect(categories2).size.toDouble()
        val unionSize = categories1.union(categories2).size.toDouble()

        return if (unionSize > 0) {
            intersectionSize / unionSize
        } else {
            0.0
        }
    }

    private fun calculateMagnitude(product: YumHubMeal, nutrients: List<NutrientsItem>): Double {
        return nutrients.map { product.withOneServing(it.attr) }.toDoubleArray().let { vector ->
            sqrt(vector.fold(0.0) { acc, value -> acc + value * value })
        }
    }
}

