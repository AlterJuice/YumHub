package com.alterjuice.meals.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alterjuice.compose_utils.ui.LocalAppNavController
import com.alterjuice.compose_utils.ui.extensions.rememberCreateRef
import com.alterjuice.compose_utils.ui.extensions.surface
import com.alterjuice.meals.viewmodels.MealsViewModelImpl
import com.alterjuice.navigation.routes.DefinedRoutes

@Composable
fun MealsScreen(
    modifier: Modifier
) {

    val vm = remember {
        MealsViewModelImpl()
    }
    val categories by vm.actualCategories.collectAsState()
    val navController = LocalAppNavController.current
    LazyColumn(
        modifier = Modifier.fillMaxSize()

    ) {

        items(categories) { category ->
            val dishes by vm.getDishesByCategory(category).collectAsState()
            ConstraintLayout(
                modifier = Modifier.surface(
                    shape = RoundedCornerShape(10f),
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    border = null,
                    elevation = 12.dp
                )
            ) {
                val titleRef = rememberCreateRef()
                val dishesRef = rememberCreateRef()
                Text(
                    modifier = Modifier.constrainAs(titleRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },
                    text = "Category: ${category.name}"
                )
                LazyRow(
                    modifier = Modifier
                        .constrainAs(dishesRef) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(titleRef.bottom)
                            bottom.linkTo(parent.bottom)
                        }
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(dishes) { dish ->
                        Text(
                            text = remember(dish) { dish.getDescription() },
                            modifier = Modifier
                                .surface(
                                    shape = RoundedCornerShape(10f),
                                    backgroundColor = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                    elevation = 5.dp
                                )
                                .width(340.dp)
                                .padding(16.dp).clickable {
                                    navController.navigate(DefinedRoutes.MealPage(dish.id))
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun MealsScreenPreview() {
    MealsScreen(
        modifier = Modifier
    )
}