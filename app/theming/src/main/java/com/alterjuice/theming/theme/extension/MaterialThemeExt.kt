package com.alterjuice.theming.theme.extension

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.alterjuice.theming.theme.LocalElevationsSchema
import com.alterjuice.theming.theme.LocalPrimaryVariantColorSchema
import com.alterjuice.theming.theme.model.ElevationsSchema
import com.alterjuice.theming.theme.model.ExtraColorSchema

val MaterialTheme.primaryVariantSchema: ExtraColorSchema
    @Composable
    get() = LocalPrimaryVariantColorSchema.current

val MaterialTheme.elevationsSchema: ElevationsSchema
    @Composable
    get() = LocalElevationsSchema.current

