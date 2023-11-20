package com.alterjuice.repository.storage

import android.content.SharedPreferences
import com.alterjuice.android_utils.prefBooleanValue


class OnboardingStorage(
    override val preferences: SharedPreferences
): PreferenceStorage() {
    val isOnboardingPassed = prefBooleanValue(
        preferences = preferences,
        key = "isOnboardingPassed",
        defaultValue = false
    )

}
