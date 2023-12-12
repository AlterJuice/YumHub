package com.alterjuice.repository.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.alterjuice.android_utils.prefValue
import com.alterjuice.domain.model.user.UserInfo
import com.alterjuice.domain.model.user.UserInfoData
import com.google.gson.Gson

class UserInfoStorage(
    override val preferences: SharedPreferences,
    private val gson: Gson
) : PreferenceStorage() {

    val userInfo = prefValue<UserInfoData?>(
        getter = {
            val value = preferences.getString(keyUserInfo, null)
            if (value.isNullOrEmpty()) return@prefValue null
            runCatching {
                gson.fromJson(value, UserInfoData::class.java)
            }.getOrNull()
        },
        setter = {
            runCatching {
                preferences.edit {
                    putString(keyUserInfo, gson.toJson(it, UserInfoData::class.java))
                }
            }
        },
        exists = { preferences.contains(keyUserInfo) },
    )

    companion object {
        private const val keyUserInfo = "keyUserInfo"
    }

}