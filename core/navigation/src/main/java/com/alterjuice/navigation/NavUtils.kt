package com.alterjuice.navigation

import androidx.navigation.NavController
import com.alterjuice.navigation.NavCommand.Companion.getDestination
import com.alterjuice.navigation.routes.DefinedRoutes

fun NavController.navigateWithRestoration(
    navigationManager: NavigationManager, route: String
) {
    navigate(route = route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        graph.findNode(route = DefinedRoutes.Dashboard.getDestination())?.id?.let { id ->
            popUpTo(id) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
    navigationManager.savedDestinations.add(route)
}