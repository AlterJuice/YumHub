package com.alterjuice.repository.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.alterjuice.android_utils.prefBooleanValue
import com.alterjuice.android_utils.prefValue
import com.alterjuice.domain.model.user.UserInfoDataNullable
import com.google.gson.Gson


class OnboardingStorage(
    override val preferences: SharedPreferences,
    private val gson: Gson
) : PreferenceStorage() {
    val isOnboardingPassed = prefBooleanValue(
        preferences = preferences,
        key = "isOnboardingPassed",
        defaultValue = false
    )

    val temporaryUserInfo = prefValue<UserInfoDataNullable>(
        getter = {
            runCatching {
                preferences.getString(keyUserInfo, null)!!
            }.mapCatching {
                gson.fromJson(it, UserInfoDataNullable::class.java)
            }.getOrElse { UserInfoDataNullable() }
        },
        setter = {
            runCatching {
                preferences.edit {
                    putString(keyUserInfo, gson.toJson(it, UserInfoDataNullable::class.java))
                }
            }
        },
        exists = { preferences.contains(keyUserInfo) },
    )

    companion object {
        private const val keyUserInfo = "keyUserInfo"
    }

}
