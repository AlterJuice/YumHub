package com.alterjuice.meals.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterjuice.domain.ResponsewItem
import com.alterjuice.domain.jsonData
import com.alterjuice.domain.model.dishes.Dish
import com.alterjuice.domain.model.dishes.toDomain
import com.alterjuice.network.pixabay.api.PixabayAPI
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

abstract class MealItemViewModel: ViewModel() {
    abstract fun getDishByID(dishID: String): Dish
}

internal class MealItemViewModelImpl : MealItemViewModel(), KoinComponent {

    val api = get<PixabayAPI>()
    val photos = MutableStateFlow<List<String>>(emptyList())

    private val dishes by lazy {
        val g = Gson()
        val x = TypeToken.getParameterized(List::class.java, ResponsewItem::class.java)
        val items = g.fromJson<List<ResponsewItem>>(jsonData, x.type)
        items.map {
            it.toDomain()
        }
    }

    override fun getDishByID(dishID: String): Dish {
        val dish = if (dishID == "") {
            dishes.random()
        } else {
            dishes.find { it.id == dishID}?: dishes.random()
        }
        viewModelScope.launch(Dispatchers.IO) {
            val response = api.getImageByQuery(dish.name)
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
    override fun getDishByID(dishID: String): Dish {
        return Dish.empty("Empty dish")
    }

}