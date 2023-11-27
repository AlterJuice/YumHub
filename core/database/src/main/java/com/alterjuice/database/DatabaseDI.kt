package com.alterjuice.database

import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.database.category_history.CategoryEatenHistoryDao
import com.alterjuice.database.meals_history.MealsHistoryDao
import com.alterjuice.database.messages.MessagesDao
import com.alterjuice.database.nutrients_history.NutrientsHistoryDao
import com.alterjuice.database.user_measurements.UserMeasurementsHistoryDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object DatabaseDI : DIModulesHub {
    override fun modules() = arrayOf(
        databaseModule()
    )

    private fun databaseModule() = module {
        single<YumHubDatabase> {
            createYumHubDatabase(androidApplication())
        }
        single<MessagesDao> {
            get<YumHubDatabase>().messagesEntities()
        }
        single<MealsHistoryDao> {
            get<YumHubDatabase>().mealsInfoEntities()
        }
        single<NutrientsHistoryDao> {
            get<YumHubDatabase>().nutrientsHistoryEntities()
        }
        single<CategoryEatenHistoryDao> {
            get<YumHubDatabase>().categoryEatenHistoryEntities()
        }
        single<UserMeasurementsHistoryDao> {
            get<YumHubDatabase>().measurementsHistoryDao()
        }
    }
}