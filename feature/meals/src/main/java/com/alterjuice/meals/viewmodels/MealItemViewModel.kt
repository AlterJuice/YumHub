package com.alterjuice.meals.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterjuice.data.data.getMealWithRecipeItemsAsYumHubMeals
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.network.pixabay.api.PixabayAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

abstract class MealItemViewModel: ViewModel() {
    abstract fun getDishByID(dishID: String): YumHubMeal
}

internal class MealItemViewModelImpl : MealItemViewModel(), KoinComponent {

    val api = get<PixabayAPI>()
    val photos = MutableStateFlow<List<String>>(emptyList())

    val dishes by lazy {
        getMealWithRecipeItemsAsYumHubMeals()
    }

    override fun getDishByID(dishID: String): YumHubMeal {
        val dish = if (dishID == "") {
            dishes.random()
        } else {
            dishes.find { it.id == dishID}?: dishes.random()
        }
        viewModelScope.launch(Dispatchers.IO) {
            val response = api.getImageByQuery(dish.foodName)
            if (response.hits.isEmpty()) return@launch
            val takeAbout = response.hits.size.coerceIn(0, 2)
            withContext(Dispatchers.Main) {
                photos.value = response.hits.take(takeAbout).map { it.webformatURL }
            }

        }
        return dish
    }

}

internal class EmptyMealItemViewModel: MealItemViewModel() {
    override fun getDishByID(dishID: String): YumHubMeal {
        return YumHubMeal.empty("Empty dish")
    }

}