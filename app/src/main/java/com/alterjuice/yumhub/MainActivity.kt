@file:OptIn(ExperimentalMaterial3Api::class)

package com.alterjuice.yumhub

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.ScrollCaptureCallback
import android.view.ScrollCaptureSession
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alterjuice.compose_utils.ui.components.ActionBar
import com.alterjuice.compose_utils.ui.components.BrandText
import com.alterjuice.compose_utils.ui.components.YumHubCircledBackground
import com.alterjuice.compose_utils.ui.extensions.get
import com.alterjuice.navigation.NavCommand
import com.alterjuice.navigation.NavCommand.Companion.getDestination
import com.alterjuice.navigation.NavigationManager
import com.alterjuice.navigation.routes.DefinedRoutes
import com.alterjuice.theming.theme.LocalAppNavController
import com.alterjuice.theming.theme.YumHubTheme
import com.alterjuice.theming.theme.extension.primaryVariantSchema
import com.alterjuice.yumhub.navigation.YumHubNavigationBar
import com.alterjuice.yumhub.navigation.YumHubNavHost
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.function.Consumer

class MainActivity : ComponentActivity(), KoinComponent {

    private val navigationManager by inject<NavigationManager>()
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            YumHubTheme {
                val isBottomBarVisible = rememberSaveable { mutableStateOf(true) }
                var currentRoute by remember {
                    mutableStateOf<NavCommand?>(null)
                }
                LaunchedEffect(Unit) {
                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        val navCommand =
                            DefinedRoutes.allRoutes.find { destination.route.equals(it.getDestination() ) }
                        currentRoute = navCommand
                        isBottomBarVisible.value = navCommand?.isBottomNavigationVisible ?: true
                    }
                }
                CompositionLocalProvider(
                    LocalAppNavController provides navController
                ) {
                    Scaffold(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .imePadding(),
                        topBar = {
                            val title = remember(currentRoute) {
                                currentRoute?.topBarTitle
                            }
                            val isVisible = remember(currentRoute) {
                                currentRoute?.isTopBarVisible == true
                            }
                            if (isVisible) {
                                ActionBar(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    centeredTitle = title?.get().orEmpty()
                                )
                            }
                        },
                        bottomBar = {

                            val isVisible = remember(currentRoute) {
                                currentRoute?.isBottomNavigationVisible ?: true
                            }
                            if (isVisible) {
                                YumHubNavigationBar(
                                    navigationManager = navigationManager,
                                    navController = navController,
                                    isVisibleState = isBottomBarVisible
                                )
                            }
                        },
                        content = { paddings ->
                            YumHubCircledBackground {
                                YumHubNavHost(
                                    navHostController = navController,
                                    modifier = Modifier.padding(paddings),
                                    startDestination = DefinedRoutes.SplashScreen.getDestination()
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    YumHubTheme {

    }
}