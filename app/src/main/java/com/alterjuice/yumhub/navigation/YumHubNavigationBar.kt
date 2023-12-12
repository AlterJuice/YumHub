@file:OptIn(ExperimentalMaterial3Api::class)

package com.alterjuice.yumhub.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alterjuice.compose_utils.ui.extensions.rememberDerivedStateOf
import com.alterjuice.compose_utils.ui.extensions.rememberLambda
import com.alterjuice.navigation.NavCommand
import com.alterjuice.navigation.NavCommand.Companion.getDestination
import com.alterjuice.navigation.NavigationManager
import com.alterjuice.navigation.navigateWithRestoration
import com.alterjuice.navigation.routes.DefinedRoutes
import com.alterjuice.resources.R

@Composable
fun YumHubNavigationBar(
    navigationManager: NavigationManager,
    navController: NavController,
    isVisibleState: State<Boolean>
) {
    val backStackEntryState = navController.currentBackStackEntryAsState()
    val isVisible by isVisibleState
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        val dashboardNavItem = remember {
            NavMenuItem(
                name = "Dashboard",//stringResource(R.string.bottom_menu_home),
                icon = R.drawable.round_chat_24,
                navCommand = DefinedRoutes.Dashboard
            )
        }
        val mealsNavItem = remember {
            NavMenuItem(
                name = "Meals", //stringResource(R.string.bottom_menu_shop),
                icon = R.drawable.round_chat_24, // Icons.Filled.MailOutline,
                navCommand = DefinedRoutes.Meals,
            )
        }
        val historyNavItem = remember {
            NavMenuItem(
                name = "History", //stringResource(R.string.bottom_menu_shop),
                icon = R.drawable.round_chat_24, // Icons.Filled.MailOutline,
                navCommand = DefinedRoutes.History,
                badgeCount = 10
            )
        }
        val profileNavItem = remember {
            NavMenuItem(
                name = "Profile", //stringResource(R.string.bottom_menu_shop),
                icon = R.drawable.round_chat_24, // Icons.Filled.MailOutline,
                navCommand = DefinedRoutes.Profile,
            )
        }
        val onItemClick: (NavCommand) -> Unit = rememberLambda { navigationCommand ->
            navController.navigateWithRestoration(
                navigationManager = navigationManager,
                route = navigationCommand.getDestination()
            )
        }

        NavigationBar {
            val backStackEntry by backStackEntryState
            val backStackEntryRoute by rememberDerivedStateOf(backStackEntry) {
                backStackEntry?.destination?.route
            }
            YumHubNavigationBarItem(
                selected = remember(backStackEntryRoute) {
                    dashboardNavItem.navCommand.getDestination() == backStackEntryRoute
                },
                item = dashboardNavItem,
                onItemClick = onItemClick
            )
            YumHubNavigationBarItem(
                selected = remember(backStackEntryRoute) {
                    mealsNavItem.navCommand.getDestination() == backStackEntryRoute
                },
                item = mealsNavItem,
                onItemClick = onItemClick
            )
            YumHubNavigationBarItem(
                selected = remember(backStackEntryRoute) {
                    historyNavItem.navCommand.getDestination() == backStackEntryRoute
                },
                item = historyNavItem,
                onItemClick = onItemClick
            )
            YumHubNavigationBarItem(
                selected = remember(backStackEntryRoute) {
                    profileNavItem.navCommand.getDestination() == backStackEntryRoute
                },
                item = profileNavItem,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun RowScope.YumHubNavigationBarItem(
    selected: Boolean,
    item: NavMenuItem,
    onItemClick: (NavCommand) -> Unit
) {
    val icon = remember(item) { mutableIntStateOf(item.icon) }
    NavigationBarItem(
        modifier = Modifier,
        label = { Text(text = item.name) },
        alwaysShowLabel = true,
        selected = selected,
        onClick = rememberLambda { onItemClick(item.navCommand) },
        icon = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // show badge box is passed badge value is above null
                val iconContent = @Composable {
                    Icon(
                        painter = painterResource(id = icon.intValue),
                        contentDescription = item.name
                    )
                }
                if (item.badgeCount > 0) {
                    BadgedBox(
                        content = { iconContent() },
                        badge = {
                            Badge {
                                Text(
                                    text = remember(item.badgeCount) {
                                        with(item.badgeCount) {
                                            if (this > 9) "9+" else this.toString()
                                        }
                                    }
                                )
                            }
                        }
                    )
                } else {
                    iconContent()
                }
                // if (selected) { // TODO: better state handling
                //     when (item.name) {
                //         stringResource(R.string.bottom_menu_home) -> {
                //             icon.value = R.drawable.ic_home_pressed
                //         }
                //         stringResource(R.string.bottom_menu_shop) -> {
                //             icon.value = R.drawable.ic_markeplace_pressed
                //         }
                //         stringResource(R.string.bottom_menu_profile) -> {
                //             icon.value = R.drawable.ic_profile_pressed
                //         }
                //     }
                // }
            }
        }
    )
}