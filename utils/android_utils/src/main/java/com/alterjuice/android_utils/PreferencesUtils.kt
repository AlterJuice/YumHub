package com.alterjuice.android_utils

import android.content.SharedPreferences
import androidx.core.content.edit

interface PreferencesValue<T> {
    fun get(): T
    fun set(value: T)
}

fun <T> prefValue(
    getter: () -> T,
    setter: (value: T) -> Unit
) = object : PreferencesValue<T> {
    override fun get() = getter()
    override fun set(value: T) = setter(value)
}

fun prefBooleanValue(
    preferences: SharedPreferences,
    key: String,
    defaultValue: Boolean
): PreferencesValue<Boolean> = prefValue(
    getter = { preferences.getBoolean(key, defaultValue) },
    setter = { preferences.edit { putBoolean(key, it) } }
)

fun prefFloatValue(
    preferences: SharedPreferences,
    key: String,
    defaultValue: Float
): PreferencesValue<Float> = prefValue(
    getter = { preferences.getFloat(key, defaultValue) },
    setter = { preferences.edit { putFloat(key, it) } }
)

fun prefIntValue(
    preferences: SharedPreferences,
    key: String,
    defaultValue: Int
): PreferencesValue<Int> = prefValue(
    getter = { preferences.getInt(key, defaultValue) },
    setter = { preferences.edit { putInt(key, it) } }
)

fun prefLongValue(
    preferences: SharedPreferences,
    key: String,
    defaultValue: Long
): PreferencesValue<Long> = prefValue(
    getter = { preferences.getLong(key, defaultValue) },
    setter = { preferences.edit { putLong(key, it) } }
)

fun prefStringValue(
    preferences: SharedPreferences,
    key: String,
    defaultValue: String
): PreferencesValue<String> = prefValue(
    getter = { preferences.getString(key, defaultValue)?: defaultValue },
    setter = { preferences.edit { putString(key, it) } }
)

fun prefStringValue(
    preferences: SharedPreferences,
    key: String,
    defaultValue: Set<String>
): PreferencesValue<Set<String>> = prefValue(
    getter = { preferences.getStringSet(key, defaultValue)?: defaultValue },
    setter = { preferences.edit { putStringSet(key, it) } }
)