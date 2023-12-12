package com.alterjuice.meals.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterjuice.data.data.getMealWithRecipeItemsAsYumHubMeals
import com.alterjuice.domain.model.common.MealType
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.repository.MealsHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


data class AddMealScreenUIState(
    val nothing: Nothing
)

interface AddMealScreenController {
    val allMeals: StateFlow<List<YumHubMeal>>
    fun addMealToHistory(meal: YumHubMeal, mealType: MealType)

}

class AddMealViewModel(
    private val mealsHistoryRepository: MealsHistoryRepository,
): ViewModel(), AddMealScreenController {
    var isInEditMode = mutableStateOf<Boolean>(false)
        private set

    override val allMeals = MutableStateFlow<List<YumHubMeal>>(emptyList())

    init {
        viewModelScope.launch {
            allMeals.value = getMealWithRecipeItemsAsYumHubMeals()
        }
    }

    fun saveMealHistory() {

    }

    fun updateSearchQuery(query: String) {
        isInEditMode.value = query.isEmpty()

    }

    fun clearSearchQuery() {
        isInEditMode.value = false

    }

    override fun addMealToHistory(meal: YumHubMeal, mealType: MealType) {
        viewModelScope.launch(Dispatchers.IO) {
            mealsHistoryRepository.addMealInfo(meal, mealType)
        }
    }

}

