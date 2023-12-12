package com.alterjuice.meals

import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.domain.repository.MealsHistoryRepository
import com.alterjuice.meals.viewmodels.AddMealViewModel
import com.alterjuice.meals.viewmodels.MealsViewModel
import com.alterjuice.meals.viewmodels.MealsViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MealsDI: DIModulesHub {
    override fun modules() = arrayOf(
        viewModelsModule()
    )

    private fun viewModelsModule() = module {
        viewModel<AddMealViewModel> {
            AddMealViewModel(
                mealsHistoryRepository = get<MealsHistoryRepository>()
            )
        }
        viewModel<MealsViewModel> {
            MealsViewModelImpl(
                userInfoStorage = get(),
                categoriesEatenHistoryDao = get(),
                mealsHistoryRepository = get()
            )
        }
    }
}