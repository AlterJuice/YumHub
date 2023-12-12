package com.alterjuice.onboarding

import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.domain.model.user.UserMeasurements
import com.alterjuice.domain.repository.UserMeasurementsRepository
import com.alterjuice.onboarding.viewmodels.OnboardingViewModel
import com.alterjuice.repository.storage.OnboardingStorage
import com.alterjuice.repository.storage.UserInfoStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object OnboardingDI : DIModulesHub {
    override fun modules() = arrayOf(
        viewModelModule()
    )

    private fun viewModelModule() = module {
        viewModel<OnboardingViewModel> {
            OnboardingViewModel(
                onboardingStorage = get<OnboardingStorage>(),
                userInfoStorage = get<UserInfoStorage>(),
                userMeasurementsRepository = get<UserMeasurementsRepository>()
            )
        }
    }
}