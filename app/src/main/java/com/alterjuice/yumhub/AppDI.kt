package com.alterjuice.yumhub

import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.dashboard.di.DashboardDI
import com.alterjuice.data.di.DataDI
import com.alterjuice.database.DatabaseDI
import com.alterjuice.meals.MealsDI
import com.alterjuice.navigation.di.NavigationDI
import com.alterjuice.network.di.NetworkDI
import com.alterjuice.onboarding.OnboardingDI
import com.alterjuice.repository.di.RepositoryDI
import com.alterjuice.user_profile.UserProfileDI

object AppDI: DIModulesHub {
    override fun modules() = arrayOf(
        *NetworkDI.modules(),
        *NavigationDI.modules(),
        *RepositoryDI.modules(),
        *DatabaseDI.modules(),
        *DashboardDI.modules(),
        *DataDI.modules(),
        *MealsDI.modules(),
        *UserProfileDI.modules(),
        *OnboardingDI.modules(),
    )
}