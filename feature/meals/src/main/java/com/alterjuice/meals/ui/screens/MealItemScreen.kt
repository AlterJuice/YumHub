package com.alterjuice.meals.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.alterjuice.meals.viewmodels.MealItemViewModelImpl

@Composable
fun MealItemScreen(
    mealID: String
) {
    val vm = remember {
        MealItemViewModelImpl()
    }
    val photo by vm.photos.collectAsState()
    val dish = remember(mealID) {
        val dish = vm.getDishByID(mealID)

        dish
    }
    Column(Modifier.fillMaxSize()) {
        photo.forEach {
            Image(
                painter = rememberAsyncImagePainter(model = it),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(dish.getDescription())
    }
}

@Composable
@Preview
private fun MealItemScreenPreview() {
    MealItemScreen(
        mealID = "1"
    )
}