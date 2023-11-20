package com.alterjuice.theming.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import com.alterjuice.theming.theme.model.ElevationsSchema
import com.alterjuice.theming.theme.model.ExtraColorSchema

val LocalAppNavController = staticCompositionLocalOf<NavController> { error("Nav controller is not provided") }
val LocalAppNavigationManager = staticCompositionLocalOf<Any> { error("Navigation manager is not provided") }
val LocalPrimaryVariantColorSchema = staticCompositionLocalOf<ExtraColorSchema> { error("Not specified") }
val LocalElevationsSchema = staticCompositionLocalOf<ElevationsSchema> { error("NotSpecified") }