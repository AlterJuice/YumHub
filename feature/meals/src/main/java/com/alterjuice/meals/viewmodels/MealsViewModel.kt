package com.alterjuice.meals.viewmodels

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterjuice.data.analyzers.PointWisePrognosis
import com.alterjuice.data.data.getMealWithRecipeItemsAsYumHubMeals
import com.alterjuice.database.category_history.CategoryEatenHistoryDao
import com.alterjuice.domain.model.common.PredefinedYumHubMealFilters
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.repository.MealsHistoryRepository
import com.alterjuice.repository.storage.UserInfoStorage
import com.alterjuice.utils.RestartableJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class MealsViewModel: ViewModel() {
    abstract val actualCategories: StateFlow<List<PredefinedYumHubMealFilters>>
    abstract val recommendations: StateFlow<List<YumHubMeal>>
    abstract fun getDishesByCategory(categories: PredefinedYumHubMealFilters): List<YumHubMeal>
    abstract fun updatedRecommendations()
}

internal class MealsViewModelImpl(
    private val userInfoStorage: UserInfoStorage,
    private val categoriesEatenHistoryDao: CategoryEatenHistoryDao,
    private val mealsHistoryRepository: MealsHistoryRepository
): MealsViewModel() {
    override val actualCategories = MutableStateFlow<List<PredefinedYumHubMealFilters>>(emptyList())
    override val recommendations = MutableStateFlow<List<YumHubMeal>>(emptyList())
    private val dishesByCategory = HashMap<PredefinedYumHubMealFilters, List<YumHubMeal>>()

    val dishes by lazy {
        getMealWithRecipeItemsAsYumHubMeals()
    }

    override fun getDishesByCategory(categories: PredefinedYumHubMealFilters): List<YumHubMeal> {
        return dishesByCategory.getOrPut(categories) {
            dishes.filter {
                categories.check(it)
            }
        }
    }

    private val recommendationsLoaderJob = RestartableJob {
        PointWisePrognosis.calculateWeightsForProducts(
            products = dishes,
            userInfo = userInfoStorage.userInfo.get()!!,
            eatenCategories = categoriesEatenHistoryDao.getAllCategoriesAteCount().associate {
                it.mealCategory to it.categoryAteCount
            },
        ).sortedByDescending { it.second }.let {
            recommendations.value = it.map { it.first }
        }
    }

    init {
        actualCategories.value = PredefinedYumHubMealFilters.values().toList()

    }

    override fun updatedRecommendations() {
        recommendationsLoaderJob.restart(viewModelScope, Dispatchers.Default)
    }

}


@RestrictTo(RestrictTo.Scope.TESTS)
object EmptyMealsViewModel: MealsViewModel() {
    override val actualCategories = MutableStateFlow<List<PredefinedYumHubMealFilters>>(
        PredefinedYumHubMealFilters.values().toList())
    override val recommendations = MutableStateFlow<List<YumHubMeal>>(emptyList())

    override fun getDishesByCategory(categories: PredefinedYumHubMealFilters): List<YumHubMeal> {
        return listOf(YumHubMeal.empty("dish 1"), YumHubMeal.empty("dish 2"), YumHubMeal.empty("dish 3"), YumHubMeal.empty("dish 4"))
    }

    override fun updatedRecommendations() {

    }

}