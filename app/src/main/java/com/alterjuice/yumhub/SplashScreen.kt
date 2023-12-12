package com.alterjuice.yumhub

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.alterjuice.compose_utils.ui.components.SurfaceWithConstraints
import com.alterjuice.compose_utils.ui.components.YumHubOutlinedCard
import com.alterjuice.navigation.NavCommand.Companion.getDestination
import com.alterjuice.navigation.routes.DefinedRoutes
import com.alterjuice.repository.storage.OnboardingStorage
import com.alterjuice.resources.R
import com.alterjuice.theming.theme.LocalAppNavController
import kotlinx.coroutines.delay
import org.koin.compose.LocalKoinScope

@Composable
fun SplashScreen(
    modifier: Modifier
) {
    val koinScope = LocalKoinScope.current
    val navigation = LocalAppNavController.current
    val logoVisibility = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        logoVisibility.value = true
        delay(1500)
        val onboardingStorage = koinScope.get<OnboardingStorage>()
        if (!onboardingStorage.isOnboardingPassed.get()) {
            navigation.navigate(DefinedRoutes.Onboarding.getDestination()) {
                popUpTo(0)
            }
        } else {
            navigation.navigate(DefinedRoutes.Dashboard.getDestination()) {
                popUpTo(0)
            }
        }
    }
    YumHubOutlinedCard(
        modifier = modifier,
        backgroundAlpha = 0.5f,
        borderStroke = null,
        surfaceShape = RectangleShape
    ) {
        val isVisible by logoVisibility
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = isVisible,
            enter = fadeIn(tween(1000)),
            exit = fadeOut(tween(1000)),
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF91B545))) {
                        append("Yum")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFF597719))) {
                        append("Hub")
                    }
                },
                modifier = Modifier,
                color = Color.Unspecified,
                fontStyle = FontStyle(R.font.kyiv_type_sans_regular),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
private fun SplashScreenPreview() {
    SplashScreen(
        modifier = Modifier
    )
}