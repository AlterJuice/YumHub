package com.alterjuice.repository.storage

import android.content.SharedPreferences
import androidx.annotation.CallSuper
import androidx.core.content.edit


abstract class PreferenceStorage: StorageCleaner {
    protected abstract val preferences: SharedPreferences

    @CallSuper
    override suspend fun clearData() {
        preferences.edit { clear() }
    }
}
