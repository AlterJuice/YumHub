package com.alterjuice.navigation.routes

import com.alterjuice.navigation.NavRoute
import com.alterjuice.navigation.NavTemplateRoute

/** Generates string like "rootRoute/arg1/arg2/arg3" */
fun generateNavRoute(root: String, vararg args: Any): NavRoute {
    if (args.isEmpty())
        return root
    return "$root/${args.joinToString(separator = "/")}"
}

/** Generates destination string like "rootRoute/{argName1}/{argName2}/{argName3}" */
fun generateArgRoute(root: String, vararg args: String): NavTemplateRoute {
    if (args.isEmpty())
        return root
    return "$root/${args.joinToString(separator = "/") { "{$it}" }}"
}