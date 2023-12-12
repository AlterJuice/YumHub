package com.alterjuice.user_profile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterjuice.compose_utils.data.model.ModelEntry
import com.alterjuice.compose_utils.data.model.UserHistoryChartModel
import com.alterjuice.domain.model.MeasurementTypes
import com.alterjuice.domain.model.user.UserInfoDataNullable
import com.alterjuice.domain.model.user.UserInfoNullable
import com.alterjuice.domain.model.user.UserMeasurements
import com.alterjuice.domain.repository.UserMeasurementsRepository
import com.alterjuice.repository.storage.UserInfoStorage
import com.alterjuice.utils.DateTimeUtils
import com.alterjuice.utils.RestartableJob
import com.alterjuice.utils.extensions.gracefulRound
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

data class UserProfileScreenUIState(
    val userInfo: UserInfoNullable = UserInfoDataNullable(),
    val measurementsModel: UserHistoryChartModel = UserHistoryChartModel.empty()
)

interface UserProfileScreenController {
    val uiState: StateFlow<UserProfileScreenUIState>

}

class UserProfileViewModel(
    private val userMeasurementsRepository: UserMeasurementsRepository,
    private val userInfoStorage: UserInfoStorage,
): ViewModel(), UserProfileScreenController  {

    override val uiState = MutableStateFlow(UserProfileScreenUIState())


    @Deprecated("This functionality can be stored for future; " +
            "We can show weight and user's muscle, fat and other parameters in compare way")
    private val allMeasurementsCollectorJob = RestartableJob {
        userMeasurementsRepository.getAllMeasurementsByTypeFlow(
            types = listOf(MeasurementTypes.HEIGHT, MeasurementTypes.WEIGHT)
        ).map {
            it.groupBy { it.dayTimestampSec }.toList().sortedBy { it.first }
        }.map { items ->
            val entryHeight = mutableListOf<Float>()
            val entryWeight = mutableListOf<Float>()
            val dates = mutableListOf<Long>()
            var lastHeight = 0f
            var lastWeight = 0f
            items.forEach { pair ->
                dates.add(pair.first)
                pair.second.forEach { measurement ->
                    if (measurement is UserMeasurements.Height) {
                        lastHeight = measurement.height
                    }
                    if (measurement is UserMeasurements.Weight) {
                        lastWeight = measurement.weight
                    }
                }
                entryHeight.add(lastHeight)
                entryWeight.add(lastWeight)
            }
            UserHistoryChartModel(
                entries = listOf(
                    ModelEntry("Height", entryHeight),
                    ModelEntry("Weight", entryWeight)
                ),
                dates = dates,
                valueLabelFormatter = { value, entry -> value.gracefulRound().toString() },
                dateLabelFormatter = { DateTimeUtils.secToDateFormatted(it) },
            )
        }.collect { model ->
            uiState.update {
                it.copy(measurementsModel = model)
            }
        }
    }

    private val userInfoLoaderJob = RestartableJob {
        uiState.update {
            it.copy(
                userInfo = userInfoStorage.userInfo.get() as UserInfoNullable
            )
        }
    }

    private val weightMeasurementsCollectorJob = RestartableJob {
        userMeasurementsRepository.getAllMeasurementsByTypeFlow(types = listOf(MeasurementTypes.WEIGHT)).map { items ->
            val entryWeight = items.map { it.getValue() }
            val dates = items.map { it.dayTimestampSec }
            UserHistoryChartModel(
                entries = listOf(ModelEntry("Weight", entryWeight),),
                dates = dates,
                valueLabelFormatter = { value, entry -> "${value.gracefulRound()} kg" },
                dateLabelFormatter = { DateTimeUtils.secToDateFormatted(it) },
            )
        }.collect { model ->
            uiState.update {
                it.copy(measurementsModel = model)
            }
        }
    }

    init {
        weightMeasurementsCollectorJob.restart(viewModelScope, Dispatchers.Default)
        userInfoLoaderJob.restart(viewModelScope, Dispatchers.Default)
    }
}