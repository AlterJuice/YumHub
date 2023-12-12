package com.alterjuice.dashboard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterjuice.data.analyzers.WaterIntakeAnalysis
import com.alterjuice.data.data.getMealWithRecipeItemsAsYumHubMeals
import com.alterjuice.domain.model.MeasurementTypes
import com.alterjuice.domain.model.common.PredefinedYumHubMealFilters
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.user.FitnessGoal
import com.alterjuice.domain.model.user.UserInfoData
import com.alterjuice.domain.model.user.UserMeasurements
import com.alterjuice.domain.model.user.UserPAL
import com.alterjuice.domain.model.user.UserSex
import com.alterjuice.domain.repository.MealsHistoryRepository
import com.alterjuice.domain.repository.NutrientsHistoryRepository
import com.alterjuice.domain.repository.UserMeasurementsRepository
import com.alterjuice.network.nutritionix.api.NutritionixAPI
import com.alterjuice.network.nutritionix.model.request.NutrientsRequest
import com.alterjuice.network.nutritionix.model.response.NutrientFoodItemAltMeasuresDTO
import com.alterjuice.network.nutritionix.model.response.NutrientFoodItemDTO
import com.alterjuice.network.nutritionix.model.response.NutrientFoodItemMetadataDTO
import com.alterjuice.network.nutritionix.model.response.NutrientFoodItemPhotoDTO
import com.alterjuice.network.nutritionix.model.response.NutrientFoodNutrientsDTO
import com.alterjuice.network.nutritionix.model.response.NutrientFoodTagDTO
import com.alterjuice.utils.DateTimeUtils
import com.alterjuice.utils.RestartableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DailyNutrientsInfo(
    val calories: Double = 0.0,
    val carbs: Double = 0.0,
    val protein: Double = 0.0,
    val fat: Double = 0.0,

)

data class DashboardScreenUIState(
    val dailyWaterBalance: Int = 0,
    val recommendedWaterConsumption: Int? = null,
    val dailyNutrients: DailyNutrientsInfo = DailyNutrientsInfo()
)

interface DashboardScreenController {
    val uiState: StateFlow<DashboardScreenUIState>
    fun getDishesByCategory(categories: PredefinedYumHubMealFilters): StateFlow<List<YumHubMeal>>
    fun updateTodayWaterBalance(newWaterBalance: Int)
}


data class InstantItem(
    val it: String
)

internal class DashboardViewModel(
    private val nutritionixAPI: NutritionixAPI,
    private val mealsHistoryRepository: MealsHistoryRepository,
    private val nutrientsHistoryRepository: NutrientsHistoryRepository,
    private val userMeasurementsRepository: UserMeasurementsRepository
) : ViewModel(), DashboardScreenController {
    // override val actualCategories = MutableStateFlow<List<PredefinedYumHubMealFilters>>(emptyList())
    private val dishesByCategory = HashMap<PredefinedYumHubMealFilters, MutableStateFlow<List<YumHubMeal>>>()
    val nutrient = MutableStateFlow<String>("")
    val items = MutableStateFlow<List<InstantItem>>(emptyList())

    override val uiState = MutableStateFlow(DashboardScreenUIState())

    private val dailyWaterBalanceCollectorJob = RestartableJob {
        userMeasurementsRepository.getMeasurementsForDateFlow(
            dayTimestampSec = DateTimeUtils.getNormalizedStartDateTodaySec(),
            types = listOf(MeasurementTypes.WATER_BALANCE)
        ).mapNotNull { it.firstOrNull()?.asWaterBalance() }.collect { measurement ->
            uiState.update {
                it.copy(dailyWaterBalance = measurement.balanceML)
            }
        }
    }

    private val recommendedWaterConsumptionCalculationJob = RestartableJob {
        val recommended = WaterIntakeAnalysis.calculateTotalDailyWaterIntakeML(
            UserInfoData(
                weight = 100.0f,
                height = 190.0f,
                sex = UserSex.MALE,
                age = 20,
                pal = UserPAL.Sedentary,
                fitnessGoal = FitnessGoal.MaintainWeight,
                username = "user1"
            )
        ).toInt()
        uiState.update { it.copy(recommendedWaterConsumption = recommended) }
    }

    private val dailyNutrientsCollectorJob = RestartableJob {
        val dayTimestampSec = DateTimeUtils.getNormalizedStartDateTodaySec()
        nutrientsHistoryRepository.getNutrientsHistoryFlowForDate(dayTimestampSec).collect { nutrientHistory ->
            uiState.update {
                it.copy(
                    dailyNutrients = it.dailyNutrients.copy(
                        calories = nutrientHistory.energy,
                        carbs = nutrientHistory.carbs,
                        protein = nutrientHistory.proteins,
                        fat = nutrientHistory.fat,
                    )
                )
            }
        }
    }


    init {
        dailyNutrientsCollectorJob.restart(viewModelScope, Dispatchers.Default)
        dailyWaterBalanceCollectorJob.restart(viewModelScope, Dispatchers.Default)
        recommendedWaterConsumptionCalculationJob.restart(viewModelScope, Dispatchers.Default)
        // actualCategories.value = PredefinedYumHubMealFilters.values().toList()
    }

    val dishes by lazy {
        getMealWithRecipeItemsAsYumHubMeals()
    }

    override fun getDishesByCategory(categories: PredefinedYumHubMealFilters): StateFlow<List<YumHubMeal>> {
        return dishesByCategory.getOrPut(categories) {
            MutableStateFlow(
                dishes.filter {
                    categories.check(it)
                }
            )
        }
    }


    override fun updateTodayWaterBalance(newWaterBalance: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userMeasurementsRepository.addMeasurement(
                UserMeasurements.WaterBalance(
                    dayTimestampSec = DateTimeUtils.getNormalizedStartDateTodaySec(),
                    balanceML = newWaterBalance
                )
            )
        }
    }

    val x: suspend CoroutineScope.() -> Unit = {
        kotlin.runCatching {
            nutritionixAPI.proceedAutocompleteSearch("bacon")
        }.onSuccess {
            kotlin.runCatching {
                val req = nutritionixAPI.getNutrients(
                    NutrientsRequest(
                        it.common?.firstOrNull()?.foodName ?: "Bacon"
                    )
                )
                req
            }.onSuccess {
                nutrient.value = it.toString()
            }.onFailure {
                nutrient.value = it.toString()
            }
            items.value = it.common?.map { it.toString() }?.map { InstantItem(it) } ?: listOf(
                InstantItem("Empty")
            )
        }.onFailure {
            items.value = listOf(InstantItem(it.stackTraceToString()))
        }
    }
}

object EmptyDashboardViewModel : DashboardScreenController {
    override val uiState = MutableStateFlow(DashboardScreenUIState())

    override fun getDishesByCategory(categories: PredefinedYumHubMealFilters): StateFlow<List<YumHubMeal>> {
        return MutableStateFlow(
            listOf(
                YumHubMeal.empty("dish 1"),
                YumHubMeal.empty("dish 2"),
                YumHubMeal.empty("dish 3"),
                YumHubMeal.empty("dish 4")
            )
        )
    }

    override fun updateTodayWaterBalance(newWaterBalance: Int) {}

}

val nutr = NutrientFoodItemDTO(
    foodName = "fried potato",
    metadata = NutrientFoodItemMetadataDTO(any = null),
    nixBrandId = null,
    mealType = 3,
    source = 1,
    nixItemId = null,
    ndbNo = 21138,
    servingUnit = "serving medium",
    altMeasures = listOf(
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 154.0,
            measure = "serving large",
            qty = 1.0,
            seq = 3
        ),
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 117.0,
            measure = "serving medium",
            qty = 1.0,
            seq = 2
        ),
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 71.0,
            measure = "serving small",
            qty = 1.0,
            seq = 1
        ),
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 46.0,
            measure = "fries",
            qty = 10.0,
            seq = 80
        ),
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 82.0,
            measure = "waffle fries",
            qty = 10.0,
            seq = 81
        ),
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 88.0,
            measure = "wedges",
            qty = 10.0,
            seq = 82
        ),
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 88.0,
            measure = "steak fries",
            qty = 10.0,
            seq = 83
        ),
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 57.0,
            measure = "cup",
            qty = 1.0,
            seq = 84
        ),
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 100.0,
            measure = "g",
            qty = 100.0,
            seq = null
        ),
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 28.3495,
            measure = "wt.oz",
            qty = 1.0,
            seq = null
        ),
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 1.19,
            measure = "tsp",
            qty = 1.0,
            seq = 101
        ),
        NutrientFoodItemAltMeasuresDTO(
            servingWeight = 3.56,
            measure = "tbsp",
            qty = 1.0,
            seq = 102
        )
    ),
    nixItemName = null,
    photo = NutrientFoodItemPhotoDTO(
        thumb = "https://nix-tag-images.s3.amazonaws.com/564_thumb.jpg",
        highres = "https://nix-tag-images.s3.amazonaws.com/564_highres.jpg"
    ),
    brandName = null,
    servingWeightGrams = 117.0,
    fullNutrients = listOf(
        NutrientFoodNutrientsDTO(value = 4.0131, attrId = 203),
        NutrientFoodNutrientsDTO(value = 17.2341, attrId = 204),
        NutrientFoodNutrientsDTO(value = 48.4848, attrId = 205),
        NutrientFoodNutrientsDTO(value = 2.1645, attrId = 207),
        NutrientFoodNutrientsDTO(value = 365.04, attrId = 208),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 221),
        NutrientFoodNutrientsDTO(value = 45.1035, attrId = 255),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 262),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 263),
        NutrientFoodNutrientsDTO(value = 1526.85, attrId = 268),
        NutrientFoodNutrientsDTO(value = 0.351, attrId = 269),
        NutrientFoodNutrientsDTO(value = 4.446, attrId = 291),
        NutrientFoodNutrientsDTO(value = 21.06, attrId = 301),
        NutrientFoodNutrientsDTO(value = 0.9477, attrId = 303),
        NutrientFoodNutrientsDTO(value = 40.95, attrId = 304),
        NutrientFoodNutrientsDTO(value = 146.25, attrId = 305),
        NutrientFoodNutrientsDTO(value = 677.43, attrId = 306),
        NutrientFoodNutrientsDTO(value = 245.7, attrId = 307),
        NutrientFoodNutrientsDTO(value = 0.585, attrId = 309),
        NutrientFoodNutrientsDTO(value = 0.1451, attrId = 312),
        NutrientFoodNutrientsDTO(value = 0.289, attrId = 315),
        NutrientFoodNutrientsDTO(value = 1.053, attrId = 317),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 318),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 319),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 320),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 321),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 322),
        NutrientFoodNutrientsDTO(value = 1.9539, attrId = 323),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 324),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 328),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 334),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 337),
        NutrientFoodNutrientsDTO(value = 31.59, attrId = 338),
        NutrientFoodNutrientsDTO(value = 0.0585, attrId = 341),
        NutrientFoodNutrientsDTO(value = 4.2471, attrId = 342),
        NutrientFoodNutrientsDTO(value = 1.053, attrId = 343),
        NutrientFoodNutrientsDTO(value = 0.0234, attrId = 344),
        NutrientFoodNutrientsDTO(value = 0.1053, attrId = 345),
        NutrientFoodNutrientsDTO(value = 0.0351, attrId = 346),
        NutrientFoodNutrientsDTO(value = 0.0351, attrId = 347),
        NutrientFoodNutrientsDTO(value = 5.499, attrId = 401),
        NutrientFoodNutrientsDTO(value = 0.1989, attrId = 404),
        NutrientFoodNutrientsDTO(value = 0.0456, attrId = 405),
        NutrientFoodNutrientsDTO(value = 3.5147, attrId = 406),
        NutrientFoodNutrientsDTO(value = 0.6786, attrId = 410),
        NutrientFoodNutrientsDTO(value = 0.4352, attrId = 415),
        NutrientFoodNutrientsDTO(value = 35.1, attrId = 417),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 418),
        NutrientFoodNutrientsDTO(value = 43.056, attrId = 421),
        NutrientFoodNutrientsDTO(value = 50.076, attrId = 429),
        NutrientFoodNutrientsDTO(value = 13.104, attrId = 430),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 431),
        NutrientFoodNutrientsDTO(value = 35.1, attrId = 432),
        NutrientFoodNutrientsDTO(value = 35.1, attrId = 435),
        NutrientFoodNutrientsDTO(value = 0.468, attrId = 454),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 601),
        NutrientFoodNutrientsDTO(value = 0.0702, attrId = 605),
        NutrientFoodNutrientsDTO(value = 2.7331, attrId = 606),
        NutrientFoodNutrientsDTO(value = 0.0936, attrId = 607),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 608),
        NutrientFoodNutrientsDTO(value = 0.0164, attrId = 609),
        NutrientFoodNutrientsDTO(value = 0.0152, attrId = 610),
        NutrientFoodNutrientsDTO(value = 0.0047, attrId = 611),
        NutrientFoodNutrientsDTO(value = 0.0211, attrId = 612),
        NutrientFoodNutrientsDTO(value = 1.4274, attrId = 613),
        NutrientFoodNutrientsDTO(value = 0.9805, attrId = 614),
        NutrientFoodNutrientsDTO(value = 0.0819, attrId = 615),
        NutrientFoodNutrientsDTO(value = 6.8106, attrId = 617),
        NutrientFoodNutrientsDTO(value = 5.7892, attrId = 618),
        NutrientFoodNutrientsDTO(value = 0.5101, attrId = 619),
        NutrientFoodNutrientsDTO(value = 0.0047, attrId = 620),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 621),
        NutrientFoodNutrientsDTO(value = 0.0468, attrId = 624),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 625),
        NutrientFoodNutrientsDTO(value = 0.0304, attrId = 626),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 627),
        NutrientFoodNutrientsDTO(value = 0.1252, attrId = 628),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 629),
        NutrientFoodNutrientsDTO(value = 0.0047, attrId = 630),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 631),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 636),
        NutrientFoodNutrientsDTO(value = 6.9837, attrId = 645),
        NutrientFoodNutrientsDTO(value = 6.3157, attrId = 646),
        NutrientFoodNutrientsDTO(value = 0.0059, attrId = 652),
        NutrientFoodNutrientsDTO(value = 0.0129, attrId = 653),
        NutrientFoodNutrientsDTO(value = 0.0257, attrId = 654),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 662),
        NutrientFoodNutrientsDTO(value = 0.0304, attrId = 663),
        NutrientFoodNutrientsDTO(value = 0.0023, attrId = 664),
        NutrientFoodNutrientsDTO(value = 0.0199, attrId = 670),
        NutrientFoodNutrientsDTO(value = 0.0035, attrId = 671),
        NutrientFoodNutrientsDTO(value = 0.0082, attrId = 672),
        NutrientFoodNutrientsDTO(value = 0.0304, attrId = 673),
        NutrientFoodNutrientsDTO(value = 6.7813, attrId = 674),
        NutrientFoodNutrientsDTO(value = 5.7307, attrId = 675),
        NutrientFoodNutrientsDTO(value = 0.0035, attrId = 676),
        NutrientFoodNutrientsDTO(value = 0.0339, attrId = 685),
        NutrientFoodNutrientsDTO(value = 0.0094, attrId = 687),
        NutrientFoodNutrientsDTO(value = 0.0012, attrId = 689),
        NutrientFoodNutrientsDTO(value = 0.0316, attrId = 693),
        NutrientFoodNutrientsDTO(value = 0.0386, attrId = 695),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 697),
        NutrientFoodNutrientsDTO(value = 0.4762, attrId = 851),
        NutrientFoodNutrientsDTO(value = 0.0012, attrId = 852),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 853),
        NutrientFoodNutrientsDTO(value = 0.0, attrId = 858)
    ),
    tags = NutrientFoodTagDTO(
        item = "Fried potatoes",
        measure = null,
        quantity = "1.0",
        tagId = 15046
    ),
    nixBrandName = null,
    servingQty = 1.0,
    nfCalories = 365.04
)
