package com.alterjuice.user_profile

import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.domain.repository.MealsHistoryRepository
import com.alterjuice.domain.repository.NutrientsHistoryRepository
import com.alterjuice.domain.repository.UserMeasurementsRepository
import com.alterjuice.user_profile.viewmodels.UserHistoryViewModel
import com.alterjuice.user_profile.viewmodels.UserProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import java.lang.reflect.Array.get

object UserProfileDI: DIModulesHub {
    override fun modules() = arrayOf(
        viewModelsModule()
    )

    private fun viewModelsModule() = module {
        viewModel<UserHistoryViewModel> {
            UserHistoryViewModel(
                nutrientsHistoryRepository = get<NutrientsHistoryRepository>(),
                userMeasurementsRepository = get<UserMeasurementsRepository>(),
                mealsHistoryRepository = get<MealsHistoryRepository>()
            )
        }
        viewModel<UserProfileViewModel> {
            UserProfileViewModel(
                userMeasurementsRepository = get(),
                userInfoStorage = get(),
            )
        }
    }
}