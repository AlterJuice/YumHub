package com.alterjuice.repository.di

import android.content.Context
import android.content.SharedPreferences
import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.database.messages.MessagesDao
import com.alterjuice.domain.repository.MessagesRepository
import com.alterjuice.domain.repository.MessagesRepositoryExtended
import com.alterjuice.repository.datasources.MessagesLocalDataSource
import com.alterjuice.repository.datasources.MessagesRemoteDataSource
import com.alterjuice.repository.repository.MessagesRepositoryImpl
import com.alterjuice.repository.storage.OnboardingStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

object RepositoryDI: DIModulesHub {
    const val OnboardingPreferences = "Onboarding"

    override fun modules() = arrayOf(
        dataSourcesModule(),
        repositoryModule(),
        preferencesModule(),
        storageModule()
    )

    private fun dataSourcesModule() = module {
        single<MessagesLocalDataSource> {
            MessagesLocalDataSource(
                messagesDB = get<MessagesDao>()
            )
        }
        single<MessagesRemoteDataSource> {
            // TODO: Provide API
            MessagesRemoteDataSource(
                api = Unit
            )
        }
    }
    private fun repositoryModule() = module {
        factory<MessagesRepository> {
            get<MessagesRepositoryExtended>()
        }
        single<MessagesRepositoryExtended> {
            MessagesRepositoryImpl(
                local = get<MessagesLocalDataSource>(),
                remote = get<MessagesRemoteDataSource>()
            )
        }
    }

    private fun storageModule() = module {
        single<OnboardingStorage> {
            OnboardingStorage(
                preferences = get<SharedPreferences>(named(OnboardingPreferences))
            )
        }
    }

    private fun preferencesModule() = module {
        single<SharedPreferences>(named(OnboardingPreferences)) {
            androidContext().getSharedPreferences(OnboardingPreferences, Context.MODE_PRIVATE)
        }
    }
}