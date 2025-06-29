package com.alterjuice.navigation

import androidx.navigation.NamedNavArgument
import com.alterjuice.android_utils.Str

/** Route destination of "root/{argName1}/{argName2}" representation and used in composable(...) to define a route */
typealias NavTemplateRoute = String

/** NavRoute is a destination which depends and generated on the inputted nav arguments */
typealias NavRoute = String


interface NavCommand {
    val rootRoute: String
    val arguments: List<NamedNavArgument>
    val isBottomNavigationVisible: Boolean get() = true
    val isTopBarVisible: Boolean get() = true
    val topBarTitle: Str?

    companion object {
        /* Do not move these functions to interface main body */
        fun NavCommand.getNavRoute(vararg args: Any): NavRoute {
            if (arguments.isEmpty() || args.isEmpty()) return rootRoute
            return "$rootRoute/${args.joinToString(separator = "/")}"
        }

        /* Do not move these functions to interface main body */
        fun NavCommand.getDestination(): NavTemplateRoute {
            if (arguments.isEmpty()) return rootRoute
            return "$rootRoute/${arguments.joinToString(separator = "/") { "{${it.name}}" }}"
        }

        fun new(
            rootRoute: String,
            arguments: List<NamedNavArgument> = emptyList(),
            isBottomNavigationVisible: Boolean = true,
            isTopBarVisible: Boolean = true,
            topBarTitle: Str? = null
        ): NavCommand {
            return object : NavCommand {
                override val arguments: List<NamedNavArgument> = arguments
                override val rootRoute: String = rootRoute
                override val isBottomNavigationVisible: Boolean = isBottomNavigationVisible
                override val topBarTitle = topBarTitle
                override val isTopBarVisible = isTopBarVisible
            }
        }
    }
}
