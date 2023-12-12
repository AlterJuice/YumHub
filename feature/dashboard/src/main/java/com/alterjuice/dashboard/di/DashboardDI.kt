package com.alterjuice.dashboard.di

import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.dashboard.viewmodels.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DashboardDI: DIModulesHub {
    override fun modules() = arrayOf(
        viewModelsModule()
    )

    private fun viewModelsModule() = module {
        viewModel<DashboardViewModel> {
            DashboardViewModel(
                nutritionixAPI = get(),
                mealsHistoryRepository = get(),
                nutrientsHistoryRepository = get(),
                userMeasurementsRepository = get()

            )
        }
    }
}