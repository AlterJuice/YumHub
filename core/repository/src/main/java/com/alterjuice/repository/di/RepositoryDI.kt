package com.alterjuice.repository.di

import android.content.Context
import android.content.SharedPreferences
import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.database.category_history.CategoryEatenHistoryDao
import com.alterjuice.database.messages.MessagesDao
import com.alterjuice.database.nutrients_history.NutrientsHistoryDao
import com.alterjuice.domain.repository.MealsHistoryRepository
import com.alterjuice.domain.repository.MessagesRepository
import com.alterjuice.domain.repository.MessagesRepositoryExtended
import com.alterjuice.domain.repository.NutrientsHistoryRepository
import com.alterjuice.domain.repository.UserMeasurementsRepository
import com.alterjuice.repository.datasources.messages.MessagesLocalDataSource
import com.alterjuice.repository.datasources.messages.MessagesRemoteDataSource
import com.alterjuice.repository.repository.MealsHistoryRepositoryImpl
import com.alterjuice.repository.repository.MessagesRepositoryImpl
import com.alterjuice.repository.repository.NutrientsHistoryRepositoryImpl
import com.alterjuice.repository.repository.UserMeasurementsRepositoryImpl
import com.alterjuice.repository.storage.OnboardingStorage
import com.alterjuice.repository.storage.UserInfoStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

object RepositoryDI: DIModulesHub {
    const val OnboardingPreferences = "Onboarding"
    const val UserInfoPreferences = "UserInfo"

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
        single<NutrientsHistoryRepository> {
            NutrientsHistoryRepositoryImpl(
                nutrientsHistoryDao = get<NutrientsHistoryDao>(),
                categoryEatenHistoryDao = get<CategoryEatenHistoryDao>()
            )
        }
        single<UserMeasurementsRepository>{
            UserMeasurementsRepositoryImpl(
                userMeasurementsHistoryDao = get()
            )
        }
        single<MealsHistoryRepository> {
            MealsHistoryRepositoryImpl(
                mealsHistoryDao = get(),
                nutrientsHistoryRepository = get(),
                measurementsHistoryDao = get(),
                gson = get(),
            )
        }
    }

    private fun storageModule() = module {
        single<OnboardingStorage> {
            OnboardingStorage(
                preferences = get<SharedPreferences>(named(OnboardingPreferences)),
                gson = get(),
            )
        }
        single<UserInfoStorage> {
            UserInfoStorage(
                preferences = get<SharedPreferences>(named(UserInfoPreferences)),
                gson = get()
            )
        }
    }

    private fun preferencesModule() = module {
        single<SharedPreferences>(named(OnboardingPreferences)) {
            androidContext().getSharedPreferences(OnboardingPreferences, Context.MODE_PRIVATE)
        }
        single<SharedPreferences>(named(UserInfoPreferences)) {
            androidContext().getSharedPreferences(UserInfoPreferences, Context.MODE_PRIVATE)
        }
    }
}