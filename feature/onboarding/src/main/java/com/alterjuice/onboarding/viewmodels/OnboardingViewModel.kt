package com.alterjuice.onboarding.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterjuice.android_utils.update
import com.alterjuice.domain.model.common.MealCategories
import com.alterjuice.domain.model.user.FitnessGoal
import com.alterjuice.domain.model.user.UserInfoData
import com.alterjuice.domain.model.user.UserInfoDataNullable
import com.alterjuice.domain.model.user.UserInfoNullable
import com.alterjuice.domain.model.user.UserMeasurements
import com.alterjuice.domain.model.user.UserPAL
import com.alterjuice.domain.model.user.UserSex
import com.alterjuice.domain.repository.UserMeasurementsRepository
import com.alterjuice.repository.storage.OnboardingStorage
import com.alterjuice.repository.storage.UserInfoStorage
import com.alterjuice.utils.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


data class OnboardingScreenUIState(
    val userInfo: UserInfoNullable? = null
)

interface OnboardingScreenController {
    val uiState: StateFlow<OnboardingScreenUIState>

    fun updateUserName(username: String)
    fun updateUserSex(userSex: UserSex)
    fun updateUserWeight(userWeight: Float)
    fun updateUserHeight(userHeight: Float)
    fun updateUserAge(userAge: Int)
    fun updateUserPAL(userPAL: UserPAL)
    fun updateUserFitnessGoal(fitnessGoal: FitnessGoal)
    fun updateUserFavouriteCategories(categories: List<MealCategories>)

    fun finishOnboarding(
        onSuccess: () -> Unit
    )
}

class OnboardingViewModel(
    private val onboardingStorage: OnboardingStorage,
    private val userInfoStorage: UserInfoStorage,
    private val userMeasurementsRepository: UserMeasurementsRepository,
): ViewModel(), OnboardingScreenController {
    override val uiState = MutableStateFlow(OnboardingScreenUIState(
        userInfo = onboardingStorage.temporaryUserInfo.get()
    ))

    override fun updateUserName(username: String) {
        onboardingStorage.temporaryUserInfo.update {
            it.copy(username = username)
        }
    }

    override fun updateUserSex(userSex: UserSex) {
        onboardingStorage.temporaryUserInfo.update {
            it.copy(sex = userSex)
        }
    }

    override fun updateUserWeight(userWeight: Float) {
        onboardingStorage.temporaryUserInfo.update {
            it.copy(weight = userWeight)
        }
    }

    override fun updateUserHeight(userHeight: Float) {
        onboardingStorage.temporaryUserInfo.update {
            it.copy(height = userHeight)
        }
    }

    override fun updateUserAge(userAge: Int) {
        onboardingStorage.temporaryUserInfo.update {
            it.copy(age = userAge)
        }
    }

    override fun updateUserPAL(userPAL: UserPAL) {
        onboardingStorage.temporaryUserInfo.update {
            it.copy(pal = userPAL)
        }
    }

    override fun updateUserFitnessGoal(fitnessGoal: FitnessGoal) {
        onboardingStorage.temporaryUserInfo.update {
            it.copy(fitnessGoal = fitnessGoal)
        }
    }

    override fun updateUserFavouriteCategories(categories: List<MealCategories>) {
        onboardingStorage.temporaryUserInfo.update {
            it.copy(favoriteCategories = categories)
        }
    }


    override fun finishOnboarding(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val temporary = onboardingStorage.temporaryUserInfo.get()

                userInfoStorage.userInfo.set(
                    UserInfoData(
                        username = temporary.username!!,
                        weight = temporary.weight!!,
                        height = temporary.height!!,
                        age = temporary.age!!,
                        pal = temporary.pal!!,
                        sex = temporary.sex!!,
                        fitnessGoal = temporary.fitnessGoal!!,
                        favoriteCategories = temporary.favoriteCategories!!
                    )
                )
                onboardingStorage.isOnboardingPassed.set(true)
                onboardingStorage.temporaryUserInfo.set(UserInfoDataNullable())
                userMeasurementsRepository.addMeasurement(
                    measurement = UserMeasurements.Weight(
                        dayTimestampSec = DateTimeUtils.getNormalizedStartDateTodaySec(),
                        weight = temporary.weight!!
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }
}