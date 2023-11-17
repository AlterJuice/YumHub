package com.alterjuice.meals.viewmodels

import androidx.annotation.RestrictTo
import com.alterjuice.domain.ResponsewItem
import com.alterjuice.domain.jsonData
import com.alterjuice.domain.model.dishes.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class MealsViewModel {
    abstract val actualCategories: StateFlow<List<PredefinedDishFilters>>
    abstract fun getDishesByCategory(categories: PredefinedDishFilters): StateFlow<List<Dish>>
}

internal class MealsViewModelImpl(): MealsViewModel() {
    override val actualCategories = MutableStateFlow<List<PredefinedDishFilters>>(emptyList())
    private val dishesByCategory = HashMap<PredefinedDishFilters, MutableStateFlow<List<Dish>>>()

    private val dishes by lazy {
        val g = Gson()
        val x = TypeToken.getParameterized(List::class.java, ResponsewItem::class.java)
        val items = g.fromJson<List<ResponsewItem>>(jsonData, x.type)
        items.map {
            it.toDomain()
        }
    }
    override fun getDishesByCategory(categories: PredefinedDishFilters): StateFlow<List<Dish>> {
        return dishesByCategory.getOrPut(categories) {
            MutableStateFlow(
                dishes.filter {
                    categories.check(it)
                }
            )
        }
    }

    init {
        actualCategories.value = PredefinedDishFilters.values().toList()
    }

}


@RestrictTo(RestrictTo.Scope.TESTS)
object EmptyMealsViewModel: MealsViewModel() {
    override val actualCategories = MutableStateFlow<List<PredefinedDishFilters>>(PredefinedDishFilters.values().toList())
    override fun getDishesByCategory(categories: PredefinedDishFilters): StateFlow<List<Dish>> {
        return MutableStateFlow(listOf(Dish.empty("dish 1"), Dish.empty("dish 2"), Dish.empty("dish 3"), Dish.empty("dish 4")))
    }

}