package com.alterjuice.data.analyzers

import com.alterjuice.data.data.getMealWithRecipeItemsAsYumHubMeals
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutrientsItem
import kotlin.math.sqrt

class SimilarityAnalyzer {
    private val nutrientWeight = 0.3
    private val categoryWeight = 0.5

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


    // Функция для создания матрицы схожести
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

    // Функция для расчета косинусного сходства между двумя продуктами
    private fun calculateCosineSimilarity(
        product1: YumHubMeal,
        product2: YumHubMeal
    ): Double {
        val nutrients1 = product1.nutrients
        val nutrients2 = product2.nutrients

        val dotProduct = nutrients1.mapNotNull { nutrient1 ->
            nutrients2.firstOrNull { it.attr.attrID == nutrient1.attr.attrID }?.let { nutrient2 ->
                (nutrient1.value ?: 0.0) * (nutrient2.value ?: 0.0)
            }
        }.sum()

        val magnitude1 = calculateMagnitude(nutrients1)
        val magnitude2 = calculateMagnitude(nutrients2)

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

    // Функция для расчета длины вектора питательных характеристик
    private fun calculateMagnitude(nutrients: List<NutrientsItem>): Double {
        return nutrients.map { it.value ?: 0.0 }.toDoubleArray().let { vector ->
            sqrt(vector.fold(0.0) { acc, value -> acc + value * value })
        }
    }
}


// Пример использования
fun mainEquality(): String {
    val productList = getMealWithRecipeItemsAsYumHubMeals().let {
        listOf(it.random(), it.random(), it.random())
    }
    val similarityMatrix = SimilarityAnalyzer().createSimilarityMatrixBetweenMeals(productList)

    // Теперь у вас есть матрица схожести
    return similarityMatrix.map {
        it.joinToString()
    }.joinToString("\n")
}

private fun main() {
    mainEquality()
}