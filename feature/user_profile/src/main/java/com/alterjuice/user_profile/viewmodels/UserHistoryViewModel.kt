package com.alterjuice.user_profile.viewmodels

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterjuice.compose_utils.data.model.HistoryDate
import com.alterjuice.compose_utils.data.model.ModelEntry
import com.alterjuice.compose_utils.data.model.UserHistoryChartModel
import com.alterjuice.domain.model.MealsHistory
import com.alterjuice.domain.model.MeasurementTypes
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutrientsHistory
import com.alterjuice.domain.model.user.UserInfo
import com.alterjuice.domain.model.user.UserMeasurements
import com.alterjuice.domain.repository.MealsHistoryRepository
import com.alterjuice.domain.repository.NutrientsHistoryRepository
import com.alterjuice.domain.repository.UserMeasurementsRepository
import com.alterjuice.utils.DateTimeUtils
import com.alterjuice.utils.RestartableJobArgs
import com.alterjuice.utils.WaterMLFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

sealed interface UserRegistrationStatus {
    object NotRegistered: UserRegistrationStatus
    data class InProgress(
        val userInfoSnapshot: UserInfo
    ): UserRegistrationStatus
    data class Registered(
        val userInfo: UserInfo
    ): UserRegistrationStatus
}

sealed interface HistoryResults {
    class Single(
        val waterBalance: UserMeasurements.WaterBalance?,
        val nutrientsHistory: NutrientsHistory?,
        val meals: List<MealsHistory>,
    ) : HistoryResults

    class Range(
        val waterBalanceModel: UserHistoryChartModel,
        val nutrientsModel: UserHistoryChartModel,
    ) : HistoryResults

    data object Unspecified : HistoryResults
}

@Stable
data class UserHistoryUIState(
    val historyDate: HistoryDate = HistoryDate.Unspecified,
    val userHistoryResults: HistoryResults = HistoryResults.Unspecified,
)

interface UserHistoryScreenController {
    val uiState: StateFlow<UserHistoryUIState>
    fun setPickedDate(date: HistoryDate)
    fun loadTodayData()

}

class UserHistoryViewModel(
    private val nutrientsHistoryRepository: NutrientsHistoryRepository,
    private val userMeasurementsRepository: UserMeasurementsRepository,
    private val mealsHistoryRepository: MealsHistoryRepository
) : ViewModel(), UserHistoryScreenController {

    override val uiState = MutableStateFlow(UserHistoryUIState())


    private val dataLoaderByDateJob = RestartableJobArgs<HistoryDate> { newDate ->
        val result = when (newDate) {
            is HistoryDate.Range -> {

                val waterResults = userMeasurementsRepository.getMeasurementsForDates(
                    fromDayTimestampSec = newDate.fromDayTimestampSec,
                    toDayTimestampSec = newDate.toDayTimestampSec,
                    types = listOf(MeasurementTypes.WATER_BALANCE)
                )
                val waterModel = UserHistoryChartModel(
                    entries = listOf(
                        ModelEntry(
                            legendLabel = "Water balance",
                            values = waterResults.map { it.getValue() }
                        )
                    ),
                    dates = waterResults.map { it.dayTimestampSec },
                    valueLabelFormatter = { value, entry ->
                        WaterMLFormatter.mlToLiters(value.toInt())
                    },
                    dateLabelFormatter = { DateTimeUtils.secToDateFormatted(it) }
                )


                val nutrientsHistory = nutrientsHistoryRepository.getNutrientsHistoryForDates(
                    fromDayTimestampSec = newDate.fromDayTimestampSec,
                    toDayTimestampSec = newDate.toDayTimestampSec
                )
                val nutrientsModel = UserHistoryChartModel(
                    entries = listOf(
                        ModelEntry("Protein", nutrientsHistory.map { it.proteins.toFloat() }),
                        ModelEntry("Fats", nutrientsHistory.map { it.proteins.toFloat() }),
                        ModelEntry("Carbs", nutrientsHistory.map { it.carbs.toFloat() }),
                    ),
                    dates = nutrientsHistory.map { it.dateTimestampSec },
                    valueLabelFormatter = { value, entry -> "${value}" },
                    dateLabelFormatter = { DateTimeUtils.secToDateFormatted(it) }
                )
                HistoryResults.Range(
                    waterBalanceModel = waterModel,
                    nutrientsModel = nutrientsModel
                )
            }

            is HistoryDate.Single -> {
                val waterBalance = userMeasurementsRepository.getMeasurementsForDate(
                    dayTimestampSec = newDate.dayTimestampSec,
                    types = listOf(MeasurementTypes.WATER_BALANCE)
                ).firstOrNull()?.asWaterBalance()
                val nutrientsHistory = runCatching {
                    nutrientsHistoryRepository.getNutrientsHistoryForDate(
                        dayTimestampSec = newDate.dayTimestampSec
                    )
                }.getOrNull()
                val meals = runCatching {
                    mealsHistoryRepository.getMealHistoryForDate(
                        timeMs = newDate.dayTimestampSec*1000L
                    )
                }.getOrElse { emptyList() }

                HistoryResults.Single(
                    waterBalance = waterBalance,
                    nutrientsHistory = nutrientsHistory,
                    meals = meals
                )
            }
            HistoryDate.Unspecified -> HistoryResults.Unspecified
        }
        uiState.update {
            it.copy(
                historyDate = newDate,
                userHistoryResults = result
            )
        }
    }


    override fun loadTodayData() {
        val today = DateTimeUtils.getNormalizedStartDateTodaySec()
        //setPickedDate(HistoryDate.Range(today - 5 * 24 * 60 * 60, today))
        setPickedDate(HistoryDate.Single(today))
    }

    override fun setPickedDate(date: HistoryDate) {
        dataLoaderByDateJob.restart(viewModelScope, Dispatchers.Default, value = date)
    }
}