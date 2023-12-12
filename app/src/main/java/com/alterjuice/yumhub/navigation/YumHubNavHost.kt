package com.alterjuice.yumhub.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alterjuice.chat_assistant.ui.screens.ChatAssistantScreen
import com.alterjuice.dashboard.ui.screens.DashboardScreen
import com.alterjuice.domain.model.common.MealType
import com.alterjuice.meals.ui.screens.AddMealScreen
import com.alterjuice.meals.ui.screens.MealItemScreen
import com.alterjuice.meals.ui.screens.MealsScreen
import com.alterjuice.navigation.NavCommand
import com.alterjuice.navigation.NavCommand.Companion.getDestination
import com.alterjuice.navigation.routes.DefinedRoutes
import com.alterjuice.onboarding.ui.screens.OnboardingScreen
import com.alterjuice.user_profile.ui.screens.UserHistoryScreen
import com.alterjuice.user_profile.ui.screens.UserProfileScreen
import com.alterjuice.yumhub.SplashScreen

fun NavGraphBuilder.composable(
    navCommand: NavCommand,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) = composable(
    route = navCommand.getDestination(),
    arguments = navCommand.arguments,
    content = content
)


@Composable
internal fun YumHubNavHost(
    navHostController: NavHostController,
    modifier: Modifier,
    startDestination: String = DefinedRoutes.SplashScreen.getDestination()
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(DefinedRoutes.SplashScreen) {
            SplashScreen(modifier = Modifier.fillMaxSize())
        }
        composable(DefinedRoutes.Onboarding) {
            OnboardingScreen(modifier = Modifier.fillMaxSize())
        }
        composable(DefinedRoutes.Dashboard) {
            DashboardScreen(modifier = Modifier.fillMaxSize())
        }
        composable(DefinedRoutes.Meals) {
            MealsScreen(modifier = Modifier.fillMaxSize())
        }
        composable(DefinedRoutes.MealPage) { b ->
            val mealID = b.arguments?.getString(DefinedRoutes.MealPage.argMealID) ?: ""
            MealItemScreen(mealID = mealID)
        }
        composable(DefinedRoutes.AddMeal) { b ->
            val mealTypeArg = b.arguments?.getString(DefinedRoutes.AddMeal.argMealType)?: ""
            val mealType = MealType.getByName(mealTypeArg)
            AddMealScreen(
                modifier = Modifier.fillMaxSize(),
                mealType = mealType
            )
        }
        composable(DefinedRoutes.Authentication) {

        }

        composable(DefinedRoutes.ChatAssistant) {
            ChatAssistantScreen(modifier = Modifier)
        }

        composable(DefinedRoutes.History) {
            UserHistoryScreen(Modifier.fillMaxSize())
        }

        composable(DefinedRoutes.Profile) {
            UserProfileScreen(Modifier.fillMaxSize())
        }
    }
}