package com.alterjuice.compose_utils.ui

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val AppNavController = staticCompositionLocalOf<NavController> { error("Nav controller is not provided") }