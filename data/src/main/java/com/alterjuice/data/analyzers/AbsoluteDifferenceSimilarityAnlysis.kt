package com.alterjuice.data.analyzers

import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutritionAttr
import com.alterjuice.domain.model.nutrition.NutritionEnum
import kotlin.math.abs

object AbsoluteDifferenceSimilarityAnlysis {

    val mainNutrientsToCompare = listOf(
        NutritionEnum.Fiber,
        NutritionEnum.Fat,
        NutritionEnum.Protein,
        NutritionEnum.Sugars,
        NutritionEnum.EnergyCalories
    )

    // Функція що створює та заповнює матрицю схожесті
    // Entry point
    fun createSimilarityMatrixBetweenMeals(
        products: List<YumHubMeal>,
        nutrientsToCompare: List<NutritionEnum> = mainNutrientsToCompare
    ): Array<DoubleArray> {
        // Розрахунок матриці схожості для кожного нутрієнта
        val matrices = nutrientsToCompare.map { nutrient ->
            absoluteDifferenceMatrix(products, nutrient)
                .let(::toSimilarityMatrix)
        }
        // val categorySimilarityMatrix =
        //     calculateSimilarityMatrixByCategories(
        //         products = products
        //     )

        // Об'єднання матриць схожості
        val aggregated = aggregateSimilarityMatrices(
            productsSize = products.size,
            matrices = matrices //+ listOf(categorySimilarityMatrix)
        )
        return aggregated
    }

    // SubEntry point
    fun absoluteDifferenceMatrix(
        products: List<YumHubMeal>,
        nutrient: NutritionAttr
    ): Array<DoubleArray> {
        val matrixSize = products.size
        return Array(matrixSize) { i ->
            DoubleArray(matrixSize) { j ->
                if (i == j) {
                    0.0
                } else {
                    absoluteDifference(
                        products[i].withServings(1.0, nutrient),
                        products[j].withServings(1.0, nutrient)
                    )
                }
            }
        }
    }

    // post entry point
    fun aggregateSimilarityMatrices(
        productsSize: Int,
        matrices: List<Array<DoubleArray>>
    ): Array<DoubleArray> {
        return Array(productsSize) { i ->
            DoubleArray(productsSize) { j ->
                matrices.map { matrix ->
                    val value = matrix[i][j]
                    if (value.isNaN()) 1.0 else value
                }.let { it.sum().div(it.size) }
            }
        }
    }

    // SubEntry point
    fun calculateSimilarityMatrixByCategories(
        products: List<YumHubMeal>
    ): Array<DoubleArray> {
        return Array(products.size) { i ->
            DoubleArray(products.size) { j ->
                if (i == j) {
                    1.0
                } else {
                    calculateCategorySimilarity(
                        product1 = products[i],
                        product2 = products[j]
                    )
                }
            }
        }
    }


    private fun toSimilarityMatrix(
        absoluteDifferenceMatrix: Array<DoubleArray>
    ): Array<DoubleArray> {
        val maxDifference = absoluteDifferenceMatrix.flatMap { it.toList() }.maxOrNull()?: 1.0
        return Array(absoluteDifferenceMatrix.size) { i ->
            DoubleArray(absoluteDifferenceMatrix[i].size) { j ->
                1.0f - absoluteDifferenceMatrix[i][j].div(maxDifference)
            }
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

    private fun absoluteDifference(a: Double, b: Double): Double {
        return abs(a - b)
    }
}