package com.alterjuice.database

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alterjuice.database.meals_history.MealsHistoryDB
import com.alterjuice.database.meals_history.MealsHistoryDao
import com.alterjuice.database.messages.MessageEntityDB
import com.alterjuice.database.messages.MessagesDao
import com.alterjuice.database.water_balance.WaterBalanceDB
import com.alterjuice.database.water_balance.WaterBalanceDao


@Database(
    entities = [MessageEntityDB::class, WaterBalanceDB::class, MealsHistoryDB::class],
    version = 2,
    exportSchema = true,
    autoMigrations = arrayOf(
        AutoMigration(1, 2)
    )
)
abstract class YumHubDatabase: RoomDatabase() {
    abstract fun messagesEntities(): MessagesDao
    abstract fun waterBalanceEntities(): WaterBalanceDao
    abstract fun mealsInfoEntities(): MealsHistoryDao
}


fun createYumHubDatabase(application: Application) = Room.databaseBuilder(
    context = application,
    klass = YumHubDatabase::class.java,
    name = "YumHubDatabase"
).build()