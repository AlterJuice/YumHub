package com.alterjuice.navigation.routes

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.alterjuice.android_utils.Str
import com.alterjuice.domain.model.common.MealType
import com.alterjuice.navigation.NavCommand
import com.alterjuice.navigation.NavCommand.Companion.getNavRoute
import com.alterjuice.navigation.NavRoute


object DefinedRoutes {
    val SplashScreen = NavCommand.new("splashscreen",
        isBottomNavigationVisible = false,
        isTopBarVisible = false
    )

    val Onboarding = NavCommand.new("onboarding",
        isBottomNavigationVisible = false,
        isTopBarVisible = false
    )
    val ChatAssistant = NavCommand.new("chatAssistant")

    val Meals = NavCommand.new("meals")


    object MealPage: NavCommand by NavCommand.new("meals_page",
        isBottomNavigationVisible = false,
        isTopBarVisible = false
    ) {
        const val argMealID = "mealID"

        override val arguments = listOf(
            navArgument(argMealID) { type = NavType.StringType }
        )
        operator fun invoke(mealID: String): NavRoute = getNavRoute(mealID)
    }

    val Authentication = NavCommand.new("authentication")

    object AddMeal: NavCommand by NavCommand.new(
        rootRoute = "addMeal",
        isBottomNavigationVisible = false
    ) {
        const val argMealType = "mealType"
        override val arguments = listOf(
            navArgument(argMealType) { type = NavType.StringType }
        )
        operator fun invoke(mealType: MealType): NavRoute = getNavRoute(mealType.name)
    }

    val Dashboard = NavCommand.new("dashboard")
    val History = NavCommand.new(
        rootRoute = "history",
        topBarTitle = Str("History")
    )
    val Profile = NavCommand.new(
        rootRoute = "profile",
        topBarTitle = Str("Profile")
    )

    val allRoutes by lazy {
        mutableListOf<NavCommand>(
            SplashScreen, Authentication, Onboarding, ChatAssistant,
            Meals, MealPage, AddMeal, Dashboard, History, Profile)
    }

}
