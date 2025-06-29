package com.alterjuice.compose_utils.ui.extensions

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.alterjuice.android_utils.Str
import com.alterjuice.android_utils.get


@Composable
fun isInPreviewMode(): Boolean {
    return LocalView.current.isInEditMode || LocalInspectionMode.current
}

@Composable
fun <T> rememberDerivedStateOf(calculation: () -> T): State<T> {
    return remember { derivedStateOf(calculation) }
}

fun <T> Modifier.subscribeToLayoutUpdates(
    state: MutableState<T>,
    transformer: (LayoutCoordinates) -> T
): Modifier {
    return this.onGloballyPositioned {
        state.value = transformer(it)
    }
}

fun Modifier.subscribeToLayoutRect(
    state: MutableState<Rect?>,
    transformer: (LayoutCoordinates) -> Rect
): Modifier {
    return this.subscribeToLayoutUpdates(state, transformer)
}

@Composable
fun rememberMutableLayoutCoordinates() = remember { mutableStateOf<LayoutCoordinates?>(null) }

@Composable
fun rememberMutableRect() = remember { mutableStateOf<Rect?>(null) }

@Composable
fun <T> rememberDerivedStateOf(key1: Any?, calculation: () -> T): State<T> {
    return remember(key1) { derivedStateOf(calculation) }
}

@Composable
fun Modifier.emptyClickable() = clickable(
    enabled = false,
    interactionSource = remember { MutableInteractionSource() },
    indication = null,
    onClick = rememberLambda { /*Nothing to do*/ }
)

@Composable
fun surfaceColorAtElevation(color: Color, elevation: Dp): Color {
    return if (color == MaterialTheme.colorScheme.surface) {
        MaterialTheme.colorScheme.surfaceColorAtElevation(elevation)
    } else {
        color
    }
}

@Composable
fun <T> rememberDerivedStateOf(key1: Any?, key2: Any?, calculation: () -> T): State<T> {
    return remember(key1, key2) { derivedStateOf(calculation) }
}

@Composable
fun <T> rememberDerivedStateOf(key1: Any?, key2: Any?, key3: Any?, calculation: () -> T): State<T> {
    return remember(key1, key2, key3) { derivedStateOf(calculation) }
}


@Composable
fun <R> rememberWithDensity(key: Any = Unit, block: Density.() -> R): R {
    val density = LocalDensity.current
    return remember(key) { with(density) { block() } }
}

@Composable
fun <R> rememberWithDensity(key: Any = Unit, key2: Any = Unit, block: Density.() -> R): R {
    val density = LocalDensity.current
    return remember(key, key2) { with(density) { block() } }
}


@Composable
fun <T> rememberLambda(lambdaBlock: T): T {
    return remember { lambdaBlock }
}
@Composable
fun <T> rememberLambda(key1: Any, lambdaBlock: T): T {
    return remember(key1) { lambdaBlock }
}

@Composable
inline fun ConstraintLayoutScope.rememberCreateRef() = remember { createRef() }

@Composable
fun Str.get(): String {
    val context = LocalContext.current
    return this.get(context)
}


@Composable
fun rememberStr(str: Str): String {
    val context = LocalContext.current
    return remember(str) { str.get(context) }
}

@Composable
fun rememberStr(context: Context, str: Str): String {
    return remember(str) { str.get(context) }
}