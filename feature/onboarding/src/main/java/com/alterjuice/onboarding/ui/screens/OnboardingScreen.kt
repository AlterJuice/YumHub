@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.alterjuice.onboarding.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.compose_utils.ui.components.YumHubOutlinedCard
import com.alterjuice.compose_utils.ui.components.YumHubOutlinedCardColumnWithCardTitle
import com.alterjuice.compose_utils.ui.extensions.rememberLambda
import com.alterjuice.domain.model.common.MealCategories
import com.alterjuice.domain.model.user.FitnessGoal
import com.alterjuice.domain.model.user.UserPAL
import com.alterjuice.domain.model.user.UserSex
import com.alterjuice.navigation.NavCommand.Companion.getDestination
import com.alterjuice.navigation.routes.DefinedRoutes
import com.alterjuice.onboarding.viewmodels.OnboardingScreenController
import com.alterjuice.onboarding.viewmodels.OnboardingViewModel
import com.alterjuice.resources.R
import com.alterjuice.theming.theme.LocalAppNavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    modifier: Modifier,
    controller: OnboardingScreenController = koinViewModel<OnboardingViewModel>()
) = YumHubOutlinedCard(
    modifier = modifier,
    backgroundAlpha = 0.5f,
    borderStroke = null,
    surfaceShape = RectangleShape
) {
    val navController = LocalAppNavController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {

        val uiState by controller.uiState.collectAsState()


        var userName by rememberSaveable {
            mutableStateOf(uiState.userInfo?.username.orEmpty())
        }
        var userSex by rememberSaveable {
            mutableStateOf(uiState.userInfo?.sex)
        }
        var userWeight by rememberSaveable {
            mutableStateOf(uiState.userInfo?.weight?.toString().orEmpty())
        }
        var userHeight by rememberSaveable {
            mutableStateOf(uiState.userInfo?.height?.toString().orEmpty())
        }
        var userAge by rememberSaveable {
            mutableStateOf(uiState.userInfo?.age?.toString().orEmpty())
        }
        var userPAL by rememberSaveable {
            mutableStateOf(uiState.userInfo?.pal ?: UserPAL.Sedentary)
        }
        var userFitnessGoal by rememberSaveable {
            mutableStateOf(uiState.userInfo?.fitnessGoal ?: FitnessGoal.MaintainWeight)
        }
        val favouriteCategories = remember {
            mutableStateListOf<MealCategories>().also {
                it.addAll(uiState.userInfo?.favoriteCategories ?: emptyList())
            }
        }

        var userNameError by rememberSaveable { mutableStateOf(false) }
        var userAgeError by rememberSaveable {
            mutableStateOf(
                userAge.let { (it.toIntOrNull() ?: 0) !in 1..120 }
            )
        }
        var userWeightError by rememberSaveable { mutableStateOf(false) }
        var userHeightError by rememberSaveable { mutableStateOf(false) }

        val onMealCategorySelect: (MealCategories) -> Unit = rememberLambda { category ->
            if (category in favouriteCategories) {
                favouriteCategories.remove(category)
            } else {
                favouriteCategories.add(category)
            }
            controller.updateUserFavouriteCategories(favouriteCategories)
        }

        val onSexChipChange: (UserSex) -> Unit = rememberLambda {
            val final = if (userSex == it) UserSex.UNSPECIFIED else it
            userSex = final
            controller.updateUserSex(final)
        }
        val onFitnessGoalChange: (FitnessGoal) -> Unit = rememberLambda {
            userFitnessGoal = it
            controller.updateUserFitnessGoal(it)
        }

        val onUserPALChange: (UserPAL) -> Unit = rememberLambda {
            userPAL = it
            controller.updateUserPAL(it)
        }
        val yumHubChipModifier = remember {
            Modifier
                .wrapContentWidth()
                .heightIn(60.dp)
        }

        YumHubOutlinedCard(
            modifier = Modifier,
            backgroundAlpha = 0.5f,
            contentPaddingValues = PaddingValues(20.dp)
        ) {
            Text(
                text = remember {
                    buildAnnotatedString {
                        append("Welcome to ")
                        withStyle(style = SpanStyle(color = Color(0xFF91B545))) {
                            append("Yum")
                        }
                        withStyle(style = SpanStyle(color = Color(0xFF597719))) {
                            append("Hub")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                color = Color.Unspecified,
                fontStyle = FontStyle(R.font.kyiv_type_sans_regular),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
        YumHubOutlinedCardColumnWithCardTitle(
            modifier = Modifier,
            title = remember { "Step 1: Personal information" },
            subTitle = remember { "*Your personal information is used to give You the best recommendations" },
            backgroundAlpha = 0.5f,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                YumHubOutlinedTextField(
                    modifier = Modifier.weight(0.75f),
                    value = userName,
                    onValueChange = { input ->
                        userName = input
                        runCatching {
                            check(input.isNotEmpty())
                            input
                        }.onFailure {
                            userNameError = true
                        }.onSuccess { name ->
                            userNameError = false
                            controller.updateUserName(name)
                        }
                    },
                    label = { Text("Name") },
                    isError = userNameError,
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    )
                )

                YumHubOutlinedTextField(
                    modifier = Modifier.weight(0.25f),
                    value = userAge,
                    onValueChange = {
                        userAge = it
                        runCatching {
                            val age = if (it.isEmpty()) 0 else it.toInt()
                            check(age in 1..120)
                            age
                        }.onFailure {
                            userAgeError = true
                        }.onSuccess { age ->
                            userAgeError = false
                            controller.updateUserAge(age)
                        }
                    },
                    label = { Text("Age") },
                    isError = userAgeError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next,
                    )
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                YumHubChip(
                    selected = userSex == UserSex.MALE,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(3f),
                    onClick = rememberLambda { onSexChipChange(UserSex.MALE) },
                    label = { Text(text = "Male") },
                )
                YumHubChip(
                    selected = userSex == UserSex.FEMALE,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(3f),
                    onClick = rememberLambda { onSexChipChange(UserSex.FEMALE) },
                    label = { Text(text = "Female") }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                YumHubOutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = userWeight,
                    onValueChange = {
                        userWeight = it
                        runCatching {
                            if (it.isEmpty()) 0f else it.toFloat()
                        }.onFailure {
                            userWeightError = true
                        }.onSuccess { weight ->
                            userWeightError = false
                            controller.updateUserWeight(weight)
                        }
                    },
                    label = { Text(remember { "Weight (kg)" }) },
                    isError = userWeightError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next,
                    )
                )
                YumHubOutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = userHeight,
                    onValueChange = {
                        userHeight = it
                        runCatching {
                            if (it.isEmpty()) 0f else it.toFloat()
                        }.onFailure {
                            userHeightError = true
                        }.onSuccess { height ->
                            userHeightError = false
                            controller.updateUserHeight(height)
                        }
                    },
                    label = { Text(remember { "Height (cm)" }) },
                    isError = userHeightError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next,
                    )
                )
            }
        }

        YumHubOutlinedCardColumnWithCardTitle(
            modifier = Modifier,
            title = remember { "Step 2: Physical activity" },
            subTitle = remember { "*Your physical information helps us to give you best recommendations" },
            backgroundAlpha = 0.5f,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                UserPAL.values().forEach { pal ->
                    YumHubChip(
                        modifier = yumHubChipModifier,
                        selected = userPAL == pal,
                        onClick = rememberLambda(pal) { onUserPALChange(pal) },
                        label = {
                            Column {
                                Text(text = pal.description)
                                if (pal.subDescription.isNotEmpty()) {
                                    Text(
                                        text = "*${pal.subDescription}",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
        YumHubOutlinedCardColumnWithCardTitle(
            modifier = Modifier,
            title = remember { "Step 3: Fitness goals" },
            subTitle = remember { "Just remind yourself about your goals :)" },
            backgroundAlpha = 0.5f,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                FitnessGoal.values().forEach { goal ->
                    YumHubChip(
                        selected = userFitnessGoal == goal,
                        modifier = yumHubChipModifier,
                        onClick = rememberLambda(goal) { onFitnessGoalChange(goal) },
                        label = { Text(text = goal.description) }
                    )
                }
            }
        }

        YumHubOutlinedCardColumnWithCardTitle(
            modifier = Modifier,
            title = remember { "Step 4: Favourite categories" },
            subTitle = remember { "Let us know your favourite food categories" },
            backgroundAlpha = 0.5f,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "Cuisine",
                style = MaterialTheme.typography.titleLarge
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                MealCategories.cuisine?.forEach { cuisine ->
                    YumHubChip(
                        selected = cuisine in favouriteCategories,
                        modifier = yumHubChipModifier,
                        onClick = rememberLambda { onMealCategorySelect(cuisine) },
                        label = { Text(text = cuisine.name.removePrefix(MealCategories.Cuisine.name)) }
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Cook Type",
                style = MaterialTheme.typography.titleLarge
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                MealCategories.cookTypes?.forEach { cookType ->
                    YumHubChip(
                        selected = cookType in favouriteCategories,
                        modifier = yumHubChipModifier,
                        onClick = rememberLambda { onMealCategorySelect(cookType) },
                        label = { Text(text = cookType.name.removePrefix(MealCategories.CookType.name)) }
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Flavor",
                style = MaterialTheme.typography.titleLarge
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                MealCategories.flavors?.forEach { flavor ->
                    YumHubChip(
                        selected = flavor in favouriteCategories,
                        modifier = yumHubChipModifier,
                        onClick = rememberLambda { onMealCategorySelect(flavor) },
                        label = { Text(text = flavor.name.removePrefix(MealCategories.Flavors.name)) }
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Food Types",
                style = MaterialTheme.typography.titleLarge
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                MealCategories.types?.forEach { type ->
                    YumHubChip(
                        selected = type in favouriteCategories,
                        modifier = yumHubChipModifier,
                        onClick = rememberLambda { onMealCategorySelect(type) },
                        label = { Text(text = type.name.removePrefix(MealCategories.Type.name)) }
                    )
                }
            }
        }

        val hasErrors = remember(userName, userAge, userWeight, userHeight) {
            if (userHeight.isEmpty()) {
                userHeightError = true
            }
            if (userAge.isEmpty() || (userAge.toIntOrNull() ?: 0) !in 1..120) {
                userAgeError = true
            }
            if (userWeight.isEmpty()) {
                userWeightError = true
            }
            if (userName.isEmpty()) {
                userNameError = true
            }
            userNameError || userAgeError || userWeightError || userHeightError
        }
        val buttonColor = if (hasErrors)
            MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary

        FilledTonalButton(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(55.dp)
                .align(Alignment.CenterHorizontally),
            enabled = !hasErrors,
            onClick = {
                controller.finishOnboarding {
                    navController.navigate(DefinedRoutes.Dashboard.getDestination()) {
                        popUpTo(0)
                    }
                }
            },
            border = BorderStroke(1.dp, buttonColor)
        ) {
            Text(
                text = remember { "Save & continue" },
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Composable
fun YumHubChip(
    modifier: Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
) {
    ElevatedFilterChip(
        selected = selected,
        modifier = modifier,
        onClick = onClick,
        label = label,
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.secondary,
            selectedBorderColor = MaterialTheme.colorScheme.primary,
            borderWidth = 1.dp,
            selectedBorderWidth = 2.dp
        )
    )
}


@Composable
fun YumHubOutlinedTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit = {},
    singleLine: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = true,
        readOnly = false,
        label = label,
        singleLine = singleLine,
        isError = isError,
        maxLines = 1,
        keyboardOptions = keyboardOptions
    )
}

@Composable
@Preview
private fun OnboardingScreenPreview() {
    OnboardingScreen(
        modifier = Modifier
    )
}