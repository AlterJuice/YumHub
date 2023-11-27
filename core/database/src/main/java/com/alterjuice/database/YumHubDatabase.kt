package com.alterjuice.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alterjuice.database.category_history.CategoryEatenHistoryDB
import com.alterjuice.database.category_history.CategoryEatenHistoryDao
import com.alterjuice.database.meals_history.MealsHistoryDB
import com.alterjuice.database.meals_history.MealsHistoryDao
import com.alterjuice.database.messages.MessageEntityDB
import com.alterjuice.database.messages.MessagesDao
import com.alterjuice.database.nutrients_history.NutrientsHistoryDB
import com.alterjuice.database.nutrients_history.NutrientsHistoryDao
import com.alterjuice.database.user_measurements.UserMeasurementsHistoryDB
import com.alterjuice.database.user_measurements.UserMeasurementsHistoryDao


@Database(
    entities = [
        MessageEntityDB::class,
        MealsHistoryDB::class,
        CategoryEatenHistoryDB::class,
        NutrientsHistoryDB::class,
        UserMeasurementsHistoryDB::class
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = arrayOf()
)
abstract class YumHubDatabase : RoomDatabase() {
    abstract fun messagesEntities(): MessagesDao
    abstract fun mealsInfoEntities(): MealsHistoryDao
    abstract fun categoryEatenHistoryEntities(): CategoryEatenHistoryDao
    abstract fun measurementsHistoryDao(): UserMeasurementsHistoryDao
    abstract fun nutrientsHistoryEntities(): NutrientsHistoryDao
}


fun createYumHubDatabase(application: Application) = Room.databaseBuilder(
    context = application,
    klass = YumHubDatabase::class.java,
    name = "YumHubDatabase"
).build()